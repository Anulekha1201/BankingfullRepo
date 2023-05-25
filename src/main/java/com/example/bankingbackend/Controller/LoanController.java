package com.example.bankingbackend.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingbackend.Entity.Loans;
import com.example.bankingbackend.repository.LoanRepository;

@RestController
@RequestMapping("/api")
public class LoanController {
	
	@Autowired
	public LoanRepository loanRepository;
	
	@GetMapping("/getloans")
	public List<Loans> getLoans(){
		return loanRepository.findAll();
	}
	
	@GetMapping("/getloan/{loanId}")
	public Loans getLoanById(@PathVariable Long loanId) {
		return loanRepository.findById(loanId).orElse(null);
	}
	
	@PostMapping("/addLoan")
	public Loans addLoan(@RequestBody Loans loan) {
		return loanRepository.save(loan);
	}

	@PutMapping("/update/{loanId}")
	public String updateLoan(@PathVariable Long loanId, @RequestBody Loans loan) {
		Loans existingLoan = loanRepository.findById(loanId).orElse(null);
		if( existingLoan ==  null) {
			return "Loan Id "+loanId+" does not exist";
		}
		//Update specific fields
		if(loan.getLoanType() != null) {
			existingLoan.setLoanType(loan.getLoanType());
		}
		
		if(loan.getFirstName() != null) {
			existingLoan.setFirstName(loan.getFirstName());
		}
		
		if(loan.getLastName() != null) {
			existingLoan.setLastName(loan.getLastName());
		}
		//update the following fields as required
		if(loan.getTenure() != null) {
			existingLoan.setTenure(loan.getTenure());
		}
		
		if(loan.getInstallment() != null) {
			existingLoan.setInstallment(loan.getInstallment());
		}
		
		if(loan.getInterestRate() != 0.0) {
			existingLoan.setInterestRate(loan.getInterestRate());
		}
		
		if(loan.getTotalLoanAmt() != null) {
			existingLoan.setTotalLoanAmt(loan.getTotalLoanAmt());
		}
		
		loanRepository.save(existingLoan);
		return "Loan details are updated for LoanId : "+loanId;
	}
	
	@DeleteMapping("/delLoan/{loanId}")
	public String delLoan(@PathVariable Long loanId) {
		loanRepository.deleteById(loanId);
		return "Deleted loan of id: "+loanId;
	}
}
