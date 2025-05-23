package org.example.projectj2ee.repositories;
import org.example.projectj2ee.entities.BankAccount;
import org.example.projectj2ee.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
