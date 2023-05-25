package com.example.bankingbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankingbackend.Entity.Accounts;
import com.example.bankingbackend.repository.AccountsRepository;

@Service
public class AccountService {

	@Autowired
	private AccountsRepository accountsRepository;
	
	public boolean checkAccountExists(String accountNo)
	{
		Accounts accno = accountsRepository.findByAccountNo(accountNo);
    	if(accno==null) {
    		System.out.println("Account doesn't exist");
    		return false;
    	}
    	else {
    		if(accno.getStatus().equals("Active")) {
				return true;
			}
    		System.out.println("Account exists but in not inactive");
    		return false;
    	}
	}
	
	public Accounts getAccWithAccNo(String accountNo)
	{
		Accounts accno = accountsRepository.findByAccountNo(accountNo);
		return accno;
		
	}
	
	public void saveAccounts(Accounts account)
	{
		accountsRepository.save(account);		
	}
}
