package me.petterroea.CodeDiff;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CodeDiff extends JFrame
{
	static JButton scan;
	static JButton copy;
	static PropertiesFile md5;
	public static void main(String[] args)
	{
		if(args.length==0)
		new CodeDiff();
		if(args.length>0)
		{
			if(args[0].equals("scan"))
			{
				md5 = new PropertiesFile("files");
				Scan.scan();
			}
			else if(args[0].equals("copy"))
			{
				md5 = new PropertiesFile("files");
				if(md5.containsKey("valid"))
				{
					Copy.copy();
				}
				else
				{
					System.out.println("Please scan first");
				}
			}
		}
	}
	public CodeDiff()
	{
		md5 = new PropertiesFile("files");
		this.setTitle("CodeDiff");
		this.setSize(300, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Items
		scan = new JButton("Scan");
		copy = new JButton("Copy");
		if(!md5.containsKey("valid"))
		{
			copy.setEnabled(false);
		}
		scan.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Scan.scan();
				
			}});
		copy.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Copy.copy();
				
			}});
		this.setLayout(new GridLayout(2, 1));
		this.add(scan);
		this.add(copy);
		this.setVisible(true);
	}
}