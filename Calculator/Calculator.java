import javax.swing.*; // includes functions for JButton, JTextField, JTextArea, JRadioButton, JCheckbox, JMenu, JColorChooser etc.
import java.awt.*; // add Abstract Window Toolkit, allows the popup window
import java.awt.event.*; //record and respond to user interactions
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Calculator implements ActionListener{
	
	JFrame frame;
	JTextField textfield;
	JTextField[] textfieldArray = new JTextField[13];
	JButton[] numberButtons = new JButton[10]; //numerical button array
	JButton[] operatorButtons = new JButton[10]; //array to hold operators
	JButton[] copyButtons = new JButton[13];
	JButton negButton, addButton, subButton, mulButton, divButton;
	JButton decButton, equalButton, delButton, clrButton, historyButton;
	JButton backButton;
	JPanel panel, historyPanel;
	
	Font myFont = new Font("Arial", Font.PLAIN,32); // sets the font style
	
	double num1 = 0, num2 = 0, result = 0;
	char operator;
	int counter = 0;
	Double[] historyResult = new Double[13]; // History will be stored as a string. Can store 13 results
	
	Calculator() { // constructor
		
		frame = new JFrame("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allows exit by closing window
		frame.setSize(420,550); //frame size
		frame.setLayout(null);
		
		textfield = new JTextField(); // adds a text field at the top
		textfield.setBounds(50, 25, 300, 50); // bounds of the text field added above
		textfield.setFont(myFont); // font within the text field
		textfield.setEditable(false); // set to false so we can only enter using the buttons
		
		for (int i = 0; i < textfieldArray.length; i++) { // text fields for history results
		textfieldArray[i] = new JTextField(); 
		textfieldArray[i].setBounds(95, (30 + (i*35)), 250, 30); // spaces the text fields out vertically
		textfieldArray[i].setFont(new Font("ARIAL", Font.PLAIN, 20));
		textfieldArray[i].setEditable(false); // set to false so we cannot change the history values
		}
		
		//Declares the buttons and sets symbol for the calculator. Will not show yet
		addButton = new JButton("+");
		subButton = new JButton("-");
		mulButton = new JButton("x");
		divButton = new JButton("/");
		decButton = new JButton(".");
		equalButton = new JButton("=");
		delButton = new JButton("Delete");
		clrButton = new JButton("Clear");
		historyButton = new JButton("History");
		negButton = new JButton("(-)");
		backButton = new JButton("Back");
		
		for (int i = 0; i < copyButtons.length; i++) { // declares the copy buttons for history page
			copyButtons[i] = new JButton("Copy"); // all hold the same button symbols.
			copyButtons[i] = copyButtons[i]; // individualizes the buttons
			copyButtons[i].setFont(new Font("ARIAL", Font.PLAIN, 16)); 
			copyButtons[i].addActionListener(this);
			copyButtons[i].setFocusable(false);
			copyButtons[i].setBounds(5, (30 + (i*35)), 80, 30);
			frame.add(copyButtons[i]); // adds to frame
			copyButtons[i].setVisible(false); // but keeps invisible until back button is pressed
		}

		operatorButtons[0] = addButton; //adds the operators to an array for easy access
		operatorButtons[1] = subButton;
		operatorButtons[2] = mulButton;
		operatorButtons[3] = divButton;
		operatorButtons[4] = decButton;
		operatorButtons[5] = equalButton;
		operatorButtons[6] = delButton;
		operatorButtons[7] = clrButton;
		operatorButtons[8] = historyButton;
		operatorButtons[9] = negButton;
		
		for (int i = 0; i < 13; i++) {
			historyResult[i] = 0.00;
		}
		
		for (int i = 0; i < operatorButtons.length; i++) {
			operatorButtons[i].addActionListener(this); //allows and records an input
			operatorButtons[i].setFont(myFont);
			operatorButtons[i].setFocusable(false); // disables weird outline when selecting button
		}
		historyButton.setFont (new Font("Arial", Font.PLAIN, 11));
		clrButton.setFont (new Font("Arial", Font.PLAIN, 23));
		delButton.setFont (new Font("Arial", Font.PLAIN, 23));
		
		
		backButton.setFont(new Font("Arial", Font.PLAIN, 11));
		backButton.addActionListener(this);// adds functionality to back button
		backButton.setFocusable(false);
		
		for (int i = 0; i < 10; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));//creates number buttons for 0-9
			numberButtons[i].addActionListener(this); // copied from operator loop
			numberButtons[i].setFont(myFont);         //
			numberButtons[i].setFocusable(false);     // default is true
		}
		
		delButton.setBounds(150,430,100,50); //set the bounds for each button.
		clrButton.setBounds(255,430,100,50);
		historyButton.setBounds(5,4,70,20);
		negButton.setBounds(45,430,100,50);
		backButton.setBounds(5,4,70,20);
		
		panel = new JPanel();
		panel.setBounds(50, 100, 305, 302);
		panel.setLayout(new GridLayout(4, 4, 12, 10));// Parameters: (rows,columns,horizontal gap, vertical gap)
		//panel.setBackground(Color.GRAY); //can use this line temporarily to see the panel where we will add buttons
		
		historyPanel = new JPanel();
		historyPanel.setBounds(0, 0, 70, 30);
		historyPanel.setLayout(new GridLayout(13, 1, 12, 10));// Parameters: (rows,columns,horizontal gap, vertical gap)
		historyPanel.setBackground(Color.GRAY);
		
		panel.add(numberButtons[1]); // all just to add each individual button
		panel.add(numberButtons[2]); // they do not hold any functionality until
		panel.add(numberButtons[3]); // using actionPerformed();
		panel.add(addButton);
		panel.add(numberButtons[4]);
		panel.add(numberButtons[5]);
		panel.add(numberButtons[6]);
		panel.add(subButton);
		panel.add(numberButtons[7]);
		panel.add(numberButtons[8]);
		panel.add(numberButtons[9]);
		panel.add(mulButton);
		panel.add(decButton);
		panel.add(numberButtons[0]);
		panel.add(equalButton);
		panel.add(divButton);
		
		frame.add(negButton);
		frame.add(panel);
		frame.add(delButton);// adds buttons to the window
		frame.add(clrButton);
		frame.add(textfield);
		frame.setVisible(true);
		frame.add(historyButton);
		frame.add(backButton).setVisible(false);
	}

	public static void main(String[] args) {

		Calculator calc = new Calculator();
	}
	
	public void actionPerformed(ActionEvent e) {
	
		for (int i = 0; i < copyButtons.length; i++) {
			if (e.getSource() == copyButtons[i]) textfield.setText(String.valueOf(historyResult[i])); // takes button input for numbers and puts in the textfield.
			}
			
		for (int i = 0; i < 10; i++) {
			if (e.getSource() == numberButtons[i]) { // if the source is a numberButton it executes
				textfield.setText(textfield.getText().concat(String.valueOf(i))); // takes button input for numbers and puts in the textfield.
			}
		}
		if (e.getSource() == decButton) { 
			textfield.setText(textfield.getText().concat("."));
		}
		if (e.getSource() == addButton) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '+';
			textfield.setText("");
		}
		if (e.getSource() == subButton) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '-';
			textfield.setText("");
		}
		if (e.getSource() == mulButton) {
			num1 = Double.parseDouble(textfield.getText());
			operator = 'x';
			textfield.setText("");
		}
		if (e.getSource() == divButton) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '/';
			textfield.setText("");
		}
		if (e.getSource() == negButton) {
			double temp = Double.parseDouble(textfield.getText());
			temp *= -1;
			textfield.setText(String.valueOf(temp));
		}
		if (e.getSource() == equalButton) {
			num2 = Double.parseDouble(textfield.getText()); 
			switch (operator) {
			case '+':
				result = num1 + num2;
				break;
			case '-':
				result = num1 - num2;
				break;
			case 'x':
				result = num1 * num2;
				break;
			case '/':
				result = num1/num2;
				break;
			}
			if (counter == 13) counter = 0; //loops count when 13 results have been gathered and restarts at the top.
			historyResult[counter] = result; //assigns the result values into an array to be used for the history
			counter++;
			
			textfield.setText("");
			textfield.setText(String.valueOf(result));
		}
		if (e.getSource() == clrButton) {
			textfield.setText("");
		}
		if (e.getSource() == delButton) {
			String text = textfield.getText();
			String newText = text.substring(0, text.length() - 1);
            textfield.setText(newText);
		}
		if (e.getSource() == historyButton) {
			textfield.setVisible(false);
			
			for (int i = 0; i < operatorButtons.length; i++) {
				operatorButtons[i].setVisible(false);
				numberButtons[i].setVisible(false);
			}
			for (int i = 0; i < textfieldArray.length; i++) {
				
				textfieldArray[i].setText(String.valueOf(historyResult[i]));
				frame.add(textfieldArray[i]);
				textfieldArray[i].setVisible(true);
				copyButtons[i].setVisible(true);
				
				
				if (historyResult[i] == 0.00) textfieldArray[i].setText("Empty");
			}
			
			panel.setVisible(false);
			historyPanel.setVisible(true);
			backButton.setVisible(true);
		}
	
		else if (e.getSource() == backButton) {
			textfield.setVisible(true); 
			
			for (int i = 0; i < operatorButtons.length; i++) {
				operatorButtons[i].setVisible(true);
				numberButtons[i].setVisible(true);
			}
			for (int i = 0; i < textfieldArray.length; i++) {
				
				textfieldArray[i].removeAll();
				textfieldArray[i].setVisible(false);
				copyButtons[i].setVisible(false);
			}
			panel.setVisible(true);
			historyPanel.setVisible(false);
			
			backButton.setVisible(false);
		}
		
	}


}
