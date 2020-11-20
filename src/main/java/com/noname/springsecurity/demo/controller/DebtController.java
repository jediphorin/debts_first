package com.noname.springsecurity.demo.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noname.springsecurity.demo.crm.CrmDebt;
import com.noname.springsecurity.demo.crm.CrmDebtUpdate;
import com.noname.springsecurity.demo.entity.Debt;
import com.noname.springsecurity.demo.service.UserService;

@Controller
@RequestMapping("/debt")
public class DebtController {
	
	@Autowired
    private UserService userService;
	
	private Logger logger = Logger.getLogger(getClass().getName());

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/showDelete")
	public String showRequests (@RequestParam("debtId") int theId, Model theModel) {
		
		theModel.addAttribute("debtId", theId);
		
		return "/home";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("debtId") int theId) {
		
		userService.deleteById(theId);
		
		return "/home";
	}
	
	@GetMapping("/showDebtFormForUpdate")
	public String showDebtFormForUpdate(@RequestParam("debtId") int theId, Model theModel) {
		
		System.out.println("DEBT CONTROLLER!");
		//	get the employee from the service
		Debt theDebt = userService.findById(theId);
		
		//	set employee as a model attribute to pre-popelate the form
		theModel.addAttribute("debt", theDebt);
		
		CrmDebtUpdate crmDebtUpdate = new CrmDebtUpdate();
		
		// НАДО ЛИ ПРИСВАИВАТЬ NULL ПОСЛЕ ИСПОЛНЕНИЯ?
		userService.setId(theDebt.getIdOfDebt());
		
		System.out.println("ID = " + userService.getId());
		crmDebtUpdate.setValue(theDebt.getValue());
		theModel.addAttribute("crmDebtUpdate", crmDebtUpdate);
		
		return "debts/debt-update-form";
	}
	
	@PostMapping("/processDebtUpdateForm")
	public String processDebtUpdateForm(
			@Valid @ModelAttribute("crmDebtUpdate") CrmDebtUpdate theCrmDebtUpdate,
				BindingResult theBindingResult, 
				Model theModel) {
		
		logger.info("Processing debt update form: debtor: " + theCrmDebtUpdate.getValue());
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "debts/debt-registration-form";
	        }
        
		 System.out.println("shit!!!");
		 
        // create debt        						
        userService.updateTheDebt(theCrmDebtUpdate, userService.getId());
        
        return "debts/debt-registration-confirmation";		
	}
	
	//	форма создания долга
	@GetMapping("/showDebtRegistrationForm")
	public String showDebtRegistrationForm(Model theModel) {
		
		theModel.addAttribute("crmDebt", new CrmDebt());
		
		return "debts/debt-registration-form";
	}
	
	//	механизм, создающий долг
	@PostMapping("/processDebtRegistrationForm")
	public String processDebtRegistrationForm(
				@Valid @ModelAttribute("crmDebt") CrmDebt theCrmDebt, 
				BindingResult theBindingResult, 
				Model theModel) {
		
		logger.info("Processing debt registration form: debtor: " + theCrmDebt.getDebtorFirstName() + " " + theCrmDebt.getDebtorLastName() 
		+ "\n, creditor: " + theCrmDebt.getCreditorFirstName() + " " + theCrmDebt.getCreditorLastName());
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "debts/debt-registration-form";
	        }
        
		 System.out.println("quest!!!");
		 
        // create debt        						
        userService.createTheDebt(theCrmDebt);
        
        logger.info("Successfully a debt: " + theCrmDebt.toString());
        
        
        
        return "debts/debt-registration-confirmation";		
	}
	
	//	список долгов
	@GetMapping("/showMyDebts")
	public String showMyDebts(Model theModel) {
		
		List<Debt> debts = userService.findAllDebtsToMeByUsername();
		theModel.addAttribute("myDebtors", debts);
		
		List<Debt> myDebts = userService.findMyDebtsToPeopleByUsername();
		theModel.addAttribute("myDebts", myDebts);
		
		return "debts/my-debt";
	}
	
}