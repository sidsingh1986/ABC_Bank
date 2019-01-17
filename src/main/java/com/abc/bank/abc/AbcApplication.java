package com.abc.bank.abc;

import com.abc.bank.abc.Enums.TypeOfService;
import com.abc.bank.abc.Models.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AbcApplication {

	private static SessionFactory factory;

	public static void main(String[] args) {
		SpringApplication.run(AbcApplication.class, args);
/*		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
            //Address fetchedAddress = (Address) session.get(Address.class, new Integer(2));
			//Bank fetchedBank = (Bank) session.get(Bank.class, new Integer(2));


			//Customer customer = new Customer();
			//customer.setName("siddharth");
			//customer.setPhoneNumber("9538318256");
			//customer.setTypeOfService(TypeOfService.REGULAR);

			Address address = new Address();
			address.setCity("hyderabad");
			address.setPincode("500034");

			Bank bank = new Bank();
			bank.setName("ABC Bank");

			Branch branch = new Branch();
			branch.setAddress(address);
			branch.setBank(bank);
			branch.setName("banjara hills");

            BankingService bankingService = new BankingService();
            bankingService.setName("banking service 1");

            BankingService bankingService1 = new BankingService();
            bankingService1.setName("banking service 2");

            BankingService bankingService2 = new BankingService();
            bankingService2.setName("banking service 3");

            BankingService bankingService3 = new BankingService();
            bankingService3.setName("banking service 4");


            List<BankingService> bankingServicesList = new ArrayList<>();
            bankingServicesList.add(bankingService);
            bankingServicesList.add(bankingService1);

            List<BankingService> bankingServicesList1 = new ArrayList<>();
            bankingServicesList1.add(bankingService2);
            bankingServicesList1.add(bankingService3);

            MultiCounterBankingService multiCounterBankingService = new MultiCounterBankingService();
            multiCounterBankingService.setName("multi counter service 1");
            multiCounterBankingService.setBankingServices(bankingServicesList);

            MultiCounterBankingService multiCounterBankingService1 = new MultiCounterBankingService();
            multiCounterBankingService1.setName("multi counter service 2");
            multiCounterBankingService1.setBankingServices(bankingServicesList1);

            session.save(bankingService);
            session.save(bankingService1);
            session.save(bankingService2);
            session.save(bankingService3);

            session.save(multiCounterBankingService);
            session.save(multiCounterBankingService1);

            List<BankingService> bankingServices = new ArrayList<>();
			List<MultiCounterBankingService> multiCounterBankingServices = new ArrayList<>();
			bankingServices.add(session.get(BankingService.class, new Integer(1)));
			bankingServices.add(session.get(BankingService.class, new Integer(2)));
			bankingServices.add(session.get(BankingService.class, new Integer(3)));
			bankingServices.add(session.get(BankingService.class, new Integer(4)));

			multiCounterBankingServices.add(session.get(MultiCounterBankingService.class, new Integer(1)));
			multiCounterBankingServices.add(session.get(MultiCounterBankingService.class, new Integer(2)));


			List<BankingService> bankingServices1 = new ArrayList<>();
			bankingServices1.add(session.get(BankingService.class, new Integer(1)));
			bankingServices1.add(session.get(BankingService.class, new Integer(2)));

			List<BankingService> bankingServices2 = new ArrayList<>();
			bankingServices2.add(session.get(BankingService.class, new Integer(3)));
			bankingServices2.add(session.get(BankingService.class, new Integer(4)));

			branch.setBankingServices(bankingServices);
			branch.setMultiCounterBankingServices(multiCounterBankingServices);

			List<Token> tokens = new ArrayList<>();

			Counter counter = new Counter();
			counter.setName("counter 1");
			counter.setTypeOfService(TypeOfService.REGULAR);
			counter.setServicesOffered(bankingServices1);
			counter.setTokens(tokens);

			//BankingService bankingService = new BankingService();
			//bankingService.setName("banking service 1");

			//branch.setBankingServices(bankingServices);
			//branch.setMultiCounterBankingServices(multiCounterBankingServices);
			//customer.setAddress(fetchedAddress);
			//customer.setBank(bank);
			//int customerId = (Integer) session.save(customer);

			Counter counter1 = new Counter();
			counter.setName("counter 2");
			counter1.setTypeOfService(TypeOfService.REGULAR);
			counter1.setServicesOffered(bankingServices2);
			counter1.setTokens(tokens);

			List<Counter> counters = new ArrayList<>();
			counters.add(counter);
			counters.add(counter1);

			branch.setCounters(counters);

			int branchId = (Integer) session.save(branch);


			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}*/
	}

}

