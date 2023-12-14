package com.miniproject.splitwise;

import com.miniproject.splitwise.Command.*;
import com.miniproject.splitwise.Controllers.ExpenseController;
import com.miniproject.splitwise.Controllers.GroupController;
import com.miniproject.splitwise.Controllers.SettleUpController;
import com.miniproject.splitwise.Controllers.UserAuthController;
import com.miniproject.splitwise.Repositories.GroupRepository;
import com.miniproject.splitwise.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@SpringBootApplication
@EnableJpaAuditing
public class SplitwiseApplication implements CommandLineRunner {

	@Autowired
	private CommandExecutor commandExecutor;
//	private UserAuthController userAuthController;
//	private GroupController groupController;
//	private GroupRepository groupRepository;
//	private ExpenseController expenseController;
//	private UserRepository userRepository;
//	private SettleUpController settleUpController;
	private Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		All the commands can be added at runtime
		commandExecutor.addCommand(new UserSignUpCommand());
		commandExecutor.addCommand(new UserLoginCommand());
		commandExecutor.addCommand(new AddGroupCommand());
		commandExecutor.addCommand(new AddGroupExpenseCommand());
		commandExecutor.addCommand(new AddUserExpenseCommand());
		commandExecutor.addCommand(new MyTotalCommand());
		commandExecutor.addCommand(new SettleUpGroupCommand());
		commandExecutor.addCommand(new SettleUpUserCommand());
		commandExecutor.addCommand(new ShowGroupsByUserCommand());

		while (true) {
			System.out.println("Please enter the input: ");
			String input = sc.nextLine();
			commandExecutor.execute(input);
		}
	}
}
