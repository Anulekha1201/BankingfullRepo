package com.example.bankingbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankingbackend.Entity.Accounts;
import com.example.bankingbackend.Entity.Debit;
import com.example.bankingbackend.Entity.UserInfo;

public interface DebitRepository extends JpaRepository<Debit, Long> {

	Debit findByCardNo(long cardNo);
	Debit findByAccountNo(Long accountNo);
	

}
