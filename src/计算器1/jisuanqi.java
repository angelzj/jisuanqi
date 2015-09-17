package 计算器1;
import java.awt.*;   
import java.awt.event.*;   
import java.awt.datatransfer.*;   
import javax.swing.*;   

public class  jisuanqi extends TextField implements ActionListener,ItemListener   
{   
    Frame mainFrame;                                   
    JTextField answerText;                               
    JTextField memoryState;                               
    MenuBar menuGroup;                                  
    Menu editMenu,viewMenu,helpMenu;                    
    MenuItem copyItem,pasteItem;                           
    MenuItem standardModel;                             
    CheckboxMenuItem numGroup;                           
    MenuItem aboutCal;                                   
    Button buttonBackSpace,buttonCE,buttonC;               
    Button buttonMC,buttonMR,buttonMS,buttonMADD;       
    Button buttonNum[];                                   
    Button buttonAdd,buttonSub,buttonMul,buttonDiv;      
    Button buttonDot,buttonSign,buttonEqual;            
    Button buttonSqrt,buttonMod,buttonReciprocal;          
    StringBuffer textBuffer;                               
    int numDot;                                         
    int operator;                                      
    double firstValue,secondValue,result;                 
    boolean enterSecond;                                   
    int forFirOrSec;                                      
    boolean b_remFir,b_remSec,b_remFun;                
    int remFun;                                        
    double remFir,remSec;                                 
    boolean isNumGroup=false;                            
    boolean canBeBack;                                   
    double memory;                                           
    Clipboard clipbd = getToolkit().getSystemClipboard();    
    jisuanqi()   
    {   
        initMainFrame();   
        initTextField();   
        initButtons();   
        initMenu();   
        OverallArrangement();   
        reSet();   
        mainFrame.setVisible(true);   
    }   
       void initMainFrame()   
    {   
        mainFrame = new Frame("计算器");   
        mainFrame.setSize(270,250);   
        mainFrame.setLocation(200,140);   
        mainFrame.setBackground(Color.lightGray);   
        mainFrame.setResizable(false);   
        mainFrame.addWindowListener(new WindowAdapter(){   
            public void windowClosing(WindowEvent e){   
                System.exit(0);   
            }   
        });   
    }   
       void initTextField()   
    {   
        textBuffer = new StringBuffer("");   
        answerText = new JTextField(textBuffer.toString(),22);   
        answerText.setHorizontalAlignment(JTextField.RIGHT);   
        answerText.setEditable(false);   
        answerText.setBackground(Color.white);   
        memoryState = new JTextField(2);   
        memoryState.setEditable(false);   
        memoryState.setBackground(Color.white);   
    }   
      void initButtons()   
    {   
        buttonBackSpace = new Button("BackSpace");   
        buttonBackSpace.addActionListener(this);   
        buttonCE = new Button("   CE   ");   
        buttonCE.addActionListener(this);   
        buttonC  = new Button("    C    ");   
        buttonC.addActionListener(this);   
        buttonMC = new Button("MC");   
        buttonMC.addActionListener(this);   
        buttonMR = new Button("MR");   
        buttonMR.addActionListener(this);   
        buttonMS = new Button("MS");   
        buttonMS.addActionListener(this);   
        buttonMADD = new Button("M+");   
        buttonMADD.addActionListener(this);   
        buttonNum = new Button[10];   
        for (int i=0;i<buttonNum.length ;i++ )   
        {   
            buttonNum[i] = new Button(Integer.toString(i));   
            buttonNum[i].addActionListener(this);   
            buttonNum[i].addKeyListener(new KeyAdapter(){   
                public void keyTyped(KeyEvent e){   
                    if (Character.isDigit(e.getKeyChar()))   
                    {   
                        textBuffer.append(e.getKeyChar());   
                        answerText.setText(textBuffer.toString());   
                        AddNum();   
                    }   
                }   
            });   
        }   
        buttonAdd = new Button("+");   
        buttonAdd.addActionListener(this);   
        buttonAdd.addKeyListener(new KeyAdapter(){   
            public void keyTyped(KeyEvent e){   
                if (e.getKeyChar() == '+')   
                    AddFunction(1);   
            }   
        });   
        buttonSub = new Button("-");   
        buttonSub.addActionListener(this);   
        buttonSub.addKeyListener(new KeyAdapter(){   
            public void keyTyped(KeyEvent e){   
                if (e.getKeyChar() == '-')   
                    AddFunction(2);   
            }   
        });   
        buttonMul = new Button("*");   
        buttonMul.addActionListener(this);   
        buttonMul.addKeyListener(new KeyAdapter(){   
            public void keyTyped(KeyEvent e){   
                if (e.getKeyChar() == '*')   
                    AddFunction(3);   
            }   
        });   
        buttonDiv = new Button("/");   
        buttonDiv.addActionListener(this);   
        buttonDiv.addKeyListener(new KeyAdapter(){   
            public void keyTyped(KeyEvent e){   
                if (e.getKeyChar() == '/')   
                    AddFunction(4);   
            }   
        });   
        buttonDot = new Button(".");   
        buttonDot.addActionListener(this);   
        buttonDot.addKeyListener(new KeyAdapter(){   
            public void keyTyped(KeyEvent e){   
                if (e.getKeyChar() == '.')   
                {   
                    textBuffer.append(e.getKeyChar());   
                    answerText.setText(textBuffer.toString());   
                    AddNum();   
                }   
            }   
        });   
        buttonSign = new Button("+/-");   
        buttonSign.addActionListener(this);   
        buttonEqual = new Button("=");   
        buttonEqual.addActionListener(this);   
        buttonEqual.addKeyListener(new KeyAdapter(){   
            public void keyTyped(KeyEvent e){   
                if (e.getKeyChar() == '=')   
                    OnGo();   
            }   
        });   
        buttonSqrt = new Button("sqrt");   
        buttonSqrt.addActionListener(this);   
        buttonMod = new Button("%");   
        buttonMod.addActionListener(this);   
        buttonReciprocal = new Button("1/x");   
        buttonReciprocal.addActionListener(this);   
    }   
       void initMenu()   
    {   
        menuGroup = new MenuBar();   
        editMenu = new Menu("编辑(E)");   
        viewMenu = new Menu("查看(V)");   
        helpMenu = new Menu("帮助(H)");   
        copyItem = new MenuItem("复制(C) Ctrl+C",new MenuShortcut(KeyEvent.VK_C));   
        copyItem.addActionListener(new CopyNum());   
        pasteItem = new MenuItem("粘贴(P) Ctrl+V",new MenuShortcut(KeyEvent.VK_V));   
        pasteItem.addActionListener(new PasteNum());   
        standardModel = new MenuItem("●标准型(T)");   
        standardModel.addActionListener(this);   
        numGroup = new CheckboxMenuItem("数字分组(I)",false);   
        numGroup.addItemListener(this);   
        aboutCal = new MenuItem("关于计算器(A)");   
        aboutCal.addActionListener(this);   
        menuGroup.add(editMenu);   
        menuGroup.add(viewMenu);   
        menuGroup.add(helpMenu);   
        editMenu.add(copyItem);   
        editMenu.add(pasteItem);   
        viewMenu.add(standardModel);   
        viewMenu.add(numGroup);   
        helpMenu.add(aboutCal);   
    }   
       void OverallArrangement()   
    {   
        Panel textPanel = new Panel();                               
        Panel upperPanel = new Panel();                             
        Panel centerPanel = new Panel();                            
        Panel mainPanel = new Panel();                                 
        Panel leftPanel = new Panel();                              
        Panel rightPanel = new Panel();                                
        mainFrame.setMenuBar(menuGroup);                             
        mainFrame.add(upperPanel,BorderLayout.NORTH);                  
        mainFrame.add(centerPanel);                                    
        mainFrame.add(mainPanel,BorderLayout.SOUTH);                   
        textPanel.add(answerText);                                     
        upperPanel.add(textPanel);                                 
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));   
        centerPanel.add(memoryState);                              
        centerPanel.add(buttonBackSpace);                          
        centerPanel.add(buttonCE);                                 
        centerPanel.add(buttonC);                                    
        leftPanel.setLayout(new GridLayout(4,1,0,5));                
        leftPanel.add(buttonMC);                                    
        leftPanel.add(buttonMR);                                      
        leftPanel.add(buttonMS);                                    
        leftPanel.add(buttonMADD);                                   
        mainPanel.add(leftPanel,BorderLayout.WEST);                   
        rightPanel.setLayout(new GridLayout(4,5,5,5));            
        rightPanel.add(buttonNum[7]);   
        rightPanel.add(buttonNum[8]);   
        rightPanel.add(buttonNum[9]);   
        rightPanel.add(buttonDiv);   
        rightPanel.add(buttonSqrt);   
        rightPanel.add(buttonNum[4]);   
        rightPanel.add(buttonNum[5]);   
        rightPanel.add(buttonNum[6]);   
        rightPanel.add(buttonMul);   
        rightPanel.add(buttonMod);   
        rightPanel.add(buttonNum[1]);   
        rightPanel.add(buttonNum[2]);   
        rightPanel.add(buttonNum[3]);   
        rightPanel.add(buttonSub);   
        rightPanel.add(buttonReciprocal);   
        rightPanel.add(buttonNum[0]);   
        rightPanel.add(buttonSign);   
        rightPanel.add(buttonDot);   
        rightPanel.add(buttonAdd);   
        rightPanel.add(buttonEqual);   
        mainPanel.add(rightPanel,BorderLayout.CENTER);   
       }   
     public void actionPerformed(ActionEvent e)   
    {   
        Object event = e.getSource();                  
           for (int i=0;i<buttonNum.length;i++)   
        {   
            if (event == buttonNum[i])   
            {   
                textBuffer.append(e.getActionCommand());   
                showScreen();   
                AddNum();   
                canBeBack = true;   
            }   
        }   
           if (event == buttonDot)   
        {   
            if (textBuffer.indexOf(".") == -1)   
            {   
                numDot = 1;   
                textBuffer.append(".");   
                showScreen();   
            }   
        }   
           if (event == buttonBackSpace && canBeBack == true)   
        {   
            if (textBuffer.length() > 0)   
            {   
                if (textBuffer.length()==1)   
                    textBuffer = new StringBuffer("0");   
                else textBuffer.deleteCharAt(textBuffer.length()-1);   
                showScreen();   
                AddNum();   
            }   
        }   
   
        if (event == buttonCE)   
        {   
            reSet();   
        }   
  
        if (event == buttonC)   
        {   
            reSet();   
        }   
          if (event == buttonSign)   
        {   
            if (result!=0)   
            {   
                result*=-1;   
                textBuffer=new StringBuffer(Double.toString(result));   
                AddNum();   
                showScreenValue();   
            }   
        }   
           if (event == buttonAdd) AddFunction(1);   
           if (event == buttonSub) AddFunction(2);   
           if (event == buttonMul) AddFunction(3);   
           if (event == buttonDiv) AddFunction(4);   
           if (event == buttonEqual) OnGo();   
           if (event == buttonSqrt)   
        {   
            if (result < 0)   
            {   
                answerText.setText("负数不能开根号");   
            }   
            else   
            {   
                result = Math.sqrt(result);   
                showScreenValue();   
                b_remFir = true;    remFir = result;   
                enterSecond = false;    forFirOrSec = 2;   
            }   
        }   
           if (event == buttonMod)   
        {   
            result /= 100;   
            showScreenValue();   
            b_remFir = true;    remFir = result;   
            enterSecond = false;    forFirOrSec = 2;   
        }   
           if (event == buttonReciprocal)   
        {   
            result = 1/result;   
            showScreenValue();   
            b_remFir = true;    remFir = result;   
            enterSecond = false;    forFirOrSec = 2;   
        }   
           if (event == buttonMC)   
        {   
            memory = 0;   
            memoryState.setText("");   
        }   
   
        if (event == buttonMR)   
        {   
            int resInteger = (int)memory;   
            if ( Math.abs(memory-resInteger) == 0 )   
            {   
                StringBuffer tmp = new StringBuffer(Double.toString(memory));   
                tmp.deleteCharAt(tmp.length()-1);   
                answerText.setText(tmp.toString());   
            }   
            else answerText.setText(Double.toString(memory));   
            if (isNumGroup == true)   
            {   
                showNumGroup();   
            }   
        }   
           if (event == buttonMS)   
        {   
            memory = Double.parseDouble(textBuffer.toString());   
            memoryState.setText("M");   
        }   
   
        if (event == buttonMADD)   
        {   
            memory += Double.parseDouble(textBuffer.toString());   
        }   
 
        if (event == aboutCal)   
        {   
            JOptionPane jop = new JOptionPane();   
            jop.showMessageDialog(mainFrame,"计算器1.00版\n");   
        }   
       }
    public void itemStateChanged(ItemEvent e)   
    {   
        Object event = e.getSource();   
        if (event == numGroup)   
        {   
            if (isNumGroup == true)    
            {   
                isNumGroup = false;   
                deleteNumGroup();   
            }   
            else    
            {   
                isNumGroup = true;   
                showNumGroup();   
            }   
        }   
    }   
       void reSet()   
    {   
        answerText.setText("0.");   
        textBuffer = new StringBuffer("");   
        numDot=0;   
        remFun = operator = 0;   
        firstValue = secondValue = result = 0.0;   
        enterSecond = false;   
        forFirOrSec = 1;   
        b_remFir = b_remSec = b_remFun = false;   
        remFir = remSec = 0.0;   
        canBeBack = true;   
    }   

    void showScreen()   
    {   
        if (numDot==0)   
            answerText.setText(textBuffer.toString()+".");   
        else   
            answerText.setText(textBuffer.toString());   
        if (isNumGroup == true)   
        {   
            showNumGroup();   
        }   
    }   
     void showScreenValue()   
    {   
        int resInteger = (int)result;   
        if ( Math.abs(result-resInteger) == 0 )   
        {   
            StringBuffer tmp = new StringBuffer(Double.toString(result));   
            tmp.deleteCharAt(tmp.length()-1);   
            answerText.setText(tmp.toString());   
        }   
        else answerText.setText(Double.toString(result));   
        if (isNumGroup == true)   
        {   
            showNumGroup();   
        }   
    }   
        void showNumGroup()   
    {   
        int i,cnt=0;   
        String string = answerText.getText();   
        String interge,fraction;   
        interge = fraction = string;   
        for (i=0;i<string.length();i++)   
        {   
            if (string.charAt(i)=='.')   
            {   
                interge = string.substring(0,i);   
                fraction = string.substring(i+1,string.length());   
                break;   
            }   
        }   
        StringBuffer sb = new StringBuffer(interge);   
        sb.reverse();   
        for (i=0;i<sb.length() ;i++ )   
        {   
            if (Character.isDigit(sb.charAt(i)))   
            {   
                cnt++;   
                if (cnt>3)   
                {   
                    cnt = 0;   
                    sb.insert(i,",");   
                }   
            }   
        }   
        sb.reverse();   
        answerText.setText(sb.toString()+"."+fraction);   
    }   
      
    void deleteNumGroup()   
    {   
        StringBuffer sb = new StringBuffer(answerText.getText());   
        for (int i=0;i<sb.length() ;i++ )   
        {   
            if (sb.charAt(i)==',')   
            {   
                sb.deleteCharAt(i);   
            }   
        }   
        answerText.setText(sb.toString());   
    }   
       void AddFunction(int op)   
    {    
        if (b_remFir==true && b_remFun==false && enterSecond==true)   
        {   
            if (operator == 1) result = remFir + secondValue;   
            else if (operator == 2) result = remFir - secondValue;   
            else if (operator == 3) result = remFir * secondValue;   
            else if (operator == 4) result = remFir / secondValue;   
               b_remFir = true;    remFir = result;    enterSecond = false;   
            forFirOrSec = 2;    operator = op;   
            showScreenValue();   
        }   
   
        else if (b_remFir==false && b_remFun==false && enterSecond==true)   
        {   
            if (operator == 1) result = firstValue + secondValue;   
            else if (operator == 2) result = firstValue - secondValue;   
            else if (operator == 3) result = firstValue * secondValue;   
            else if (operator == 4) result = firstValue / secondValue;   
               b_remFir = true;    remFir = result;    enterSecond = false;   
            forFirOrSec = 2;    operator = op;   
            showScreenValue();   
        }   
        else   
        {   
            operator = op;   
            forFirOrSec = 2;   
        }   
        b_remFun = false;   remFun = 0;   
        textBuffer = new StringBuffer("");   
    }   
       void AddNum()   
    {   
        if (forFirOrSec == 1)   
        {   
            result = firstValue = Double.parseDouble(textBuffer.toString());   
            b_remFir = false;   remFir = 0;   
        }   
        else if (forFirOrSec == 2)   
        {   
            enterSecond = true;   
            secondValue = Double.parseDouble(textBuffer.toString());   
            b_remSec = false;   remSec = 0;   
        }   
    }   
           void OnGo()   
    {    
        if (b_remFir==true && b_remSec==true && b_remFun==true)   
        {   
            if (remFun==1) result = remFir + remSec;   
            else if (remFun==2) result = remFir - remSec;   
            else if (remFun==3) result = remFir * remSec;   
            else if (remFun==4) result = remFir / remSec;   
   
            b_remFir=true;  b_remSec=true;  b_remFun=true;   
            remFir = result; remSec = remSec; remFun = remFun;   
        }   

        else if (b_remFir==true && b_remSec==false && b_remFun==false)   
        {   
            if (operator == 1) result = remFir + secondValue;   
            else if (operator == 2) result = remFir - secondValue;   
            else if (operator == 3) result = remFir * secondValue;   
            else if (operator == 4) result = remFir / secondValue;   
   
            b_remFir=true;  b_remSec=true;  b_remFun=true;   
            remFir = result; remSec = secondValue; remFun = remFun;   
        }   
  
        else if (b_remFir==true && b_remSec==true && b_remFun==false)   
        {   
            secondValue = remFir;   
            if (operator == 1) result = remFir + secondValue;   
            else if (operator == 2) result = remFir - secondValue;   
            else if (operator == 3) result = remFir * secondValue;   
            else if (operator == 4) result = remFir / secondValue;   
   
            b_remFir=true;  b_remSec=true;  b_remFun=true;   
            remFir = result; remSec = secondValue; remFun = remFun;   
        }   
   
        else if (b_remFir==false && b_remSec==false && b_remFun==false)   
        {   
            if (enterSecond == false) secondValue = firstValue;   
            if (operator == 1) result = firstValue + secondValue;   
            else if (operator == 2) result = firstValue - secondValue;   
            else if (operator == 3) result = firstValue * secondValue;   
            else if (operator == 4) result = firstValue / secondValue;      
            b_remFir=true;  b_remSec=true;  b_remFun=true;   
            remFir = result; remSec = secondValue; remFun = remFun;   
        }   
   
        showScreenValue();   
        textBuffer = new StringBuffer("");   
        firstValue = secondValue = 0;   
        forFirOrSec = 1;   
        enterSecond = false;   
        canBeBack = false;   
    }   
     private class CopyNum implements ActionListener   
    {   
        public void actionPerformed(ActionEvent e)   
        {   
            StringBuffer selection = new StringBuffer(answerText.getText());   
            if (selection == null)   
                return;   
            if ( selection.charAt(selection.length()-1)=='.' )   
            {   
                selection.deleteCharAt(selection.length()-1);   
            }   
            StringSelection clipString = new StringSelection(selection.toString());   
            clipbd.setContents(clipString,clipString);   
        }   
    };   
       private class PasteNum implements ActionListener   
    {   
        public void actionPerformed(ActionEvent e)   
        {   
            Transferable clipData = clipbd.getContents(jisuanqi.this);   
            try   
            {   
                String clipString = (String)clipData.getTransferData(DataFlavor.stringFlavor);   
                for (int i=0;i<clipString.length();i++)   
                {   
                    if ( !(Character.isDigit(clipString.charAt(i)) || clipString.charAt(i)=='.') )   
                    {   
                        clipString = new String("0.");   
                        break;   
                    }   
                }   
                answerText.setText(clipString);   
            }   
            catch (Exception ex)   
            {   
                System.out.println("Couldn't get contents in format: "+    
                        DataFlavor.stringFlavor.getHumanPresentableName());    
            }   
        }   
    };   
   public static void main(String args[])   
    {   
        new jisuanqi();   
    }   
}   