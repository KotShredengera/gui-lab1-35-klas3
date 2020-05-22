package com.mybank.gui;

import com.mybank.domain.Account;
import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class SWINGDemo {
    
    private final JEditorPane log;
    private final JButton show;
    private final JComboBox clients;
    
    public SWINGDemo() {
        log = new JEditorPane("text/html", "");
        log.setPreferredSize(new Dimension(450, 350));
        show = new JButton("Show report");
        clients = new JComboBox();
        for (int i=0; i<Bank.getNumberOfCustomers();i++)
        {
            clients.addItem(Bank.getCustomer(i).getLastName()+", "+Bank.getCustomer(i).getFirstName());
        }
        
    }
    
    private void launchFrame() {
        JFrame frame = new JFrame("MyBank clients");
        frame.setLayout(new BorderLayout());
        JPanel cpane = new JPanel();
        cpane.setLayout(new GridLayout(1, 2));
        
        cpane.add(clients);
        cpane.add(show);
        frame.add(cpane, BorderLayout.NORTH);
        frame.add(new JScrollPane(log), BorderLayout.CENTER);
        
        clients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer current = Bank.getCustomer(clients.getSelectedIndex());                
                String custInfo = getCustomerInfo(current);
                log.setText(custInfo);
            }
        });
        
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder report = new StringBuilder();
                
                for (int i = 0; i < Bank.getNumberOfCustomers(); i++) {
                    report.append(getCustomerInfo(Bank.getCustomer(i)));
                }
                
                log.setText(report.toString());
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setResizable(false);
        frame.setVisible(true);        
    }
    
    private String getCustomerInfo(Customer customer) {
        StringBuilder info = new StringBuilder();
        
        info.append("<br>&nbsp;<b><span style=\"font-size:2em;\">")
            .append(customer.getLastName())
            .append(", ")
            .append(customer.getFirstName())
            .append("</span><br><hr>");
        
        for (int i = 0; i < customer.getNumberOfAccounts(); i++) {
            Account account = customer.getAccount(i);
            
            info.append("&nbsp;<b>Acc Type: </b>")
                .append(account instanceof CheckingAccount ? "Checking" : "Savings")
                .append("<br>&nbsp;<b>Balance: <span style=\"color:red;\">$")
                .append(account.getBalance())
                .append("</span></b><br>");
        }
        
        return info.toString();
    }
    
    public static void main(String[] args) {
        
        Bank.addCustomer("John", "Doe");
        Bank.addCustomer("Fox", "Mulder");
        Bank.addCustomer("Dana", "Scully");
        Bank.addCustomer("John", "Marston");
        Bank.getCustomer(0).addAccount(new CheckingAccount(2000));
        Bank.getCustomer(0).addAccount(new SavingsAccount(1000, 2));
        Bank.getCustomer(1).addAccount(new SavingsAccount(1000, 3));
        Bank.getCustomer(2).addAccount(new CheckingAccount(1000, 500));
        Bank.getCustomer(3).addAccount(new CheckingAccount(1000, 500));
        
        SWINGDemo demo = new SWINGDemo();        
        demo.launchFrame();
    }
    
}
