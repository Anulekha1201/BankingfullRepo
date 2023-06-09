package com.example.bankingbackend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingbackend.Entity.Loans;
import com.example.bankingbackend.Exception.BadRequestException;
import com.example.bankingbackend.Exception.ResourceNotFoundException;
import com.example.bankingbackend.Service.DebitService;
import com.example.bankingbackend.Service.LoanService;
import com.example.bankingbackend.repository.LoanRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
public class LoanController {

	@Autowired
	public LoanService loanService;

	@Autowired
	public DebitService debitService;

	@Autowired
	public LoanRepository loanrepository;

//    @GetMapping("/getloans")
//    public List<Loans> getLoans(){
//        return loanRepository.findAll();
//    }
//    
//    @GetMapping("/getloan/{loanId}")
//    public Loans getLoanById(@PathVariable Long loanId) {
//        return loanRepository.findById(loanId).orElse(null);
//    }
//    
	@GetMapping("/api/admindashboard/LoanapprovalsHistory")
	public List<Loans> LoanapprovalsHistory() {
		List<Loans> dh = loanrepository.findByStatus("Waiting for approval");
		System.out.println(dh);
		return dh;
	}

	@GetMapping("/api/admindashboard/LoanapprovedHistory")
	public List<Loans> DebitapprovedHistory() {
		List<Loans> dh = loanrepository.findByStatus("Approved");
		System.out.println(dh);
		return dh;
	}

	@PostMapping("/api/admindashboard/updateLoanstatus/{cardNo}")
	public boolean updatestatustoapprove(@PathVariable Long cardNo) {
		Loans da = loanrepository.findByCardNo(cardNo);
		System.out.println(cardNo + " " + da.getStatus());
			da.setStatus("Approved");
			loanrepository.save(da);
			return true;

	}

	@PostMapping("api/user/applyLoan")
	public boolean addLoan(@RequestBody Loans loan) throws ResourceNotFoundException,BadRequestException{
		Long cardNo = loan.getCardNo();
		System.out.println("carNo: " + loan.getCardNo());
		if (debitService.checkDebitExists(cardNo)) {
			if (loanService.checkIfLoanExistsWithDebitCardNo(cardNo)) {
				System.out.println(loanService.checkIfLoanExistsWithDebitCardNo(cardNo));
				System.out.println(debitService.checkDebitExists(cardNo) + "\ncardNo: " + cardNo);
				System.out.println("cardNo: " + loan.getCardNo());
				loanService.applyLoan(loan);
				System.out.println("loan applied");
				return true;
			} else {
				System.out.println("loan already exists with this cardNo");
				//return false;
				throw new BadRequestException("Loan already exists with this cardNo");
			}

		} else {
			System.out.print("Debit Card number doesn't exist");
			//return false;
			throw new ResourceNotFoundException("Debit card number does't exist");
		}
	}
}
