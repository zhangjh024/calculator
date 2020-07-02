package 大作业;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.GridLayout;
public class Calculate_Ordinary extends JFrame implements ActionListener {
  private final String[] KEYS = { "7", "8", "9", "/", "√", "4", "5", "6",
             "*", "%", "1", "2", "3", "-", "1/x", "0", "+/-", ".", "+", "=" };
     /** 计算器上的功能键的显示名字      final p86*/
     private final String[] COMMAND = { "Backspace", "CE", "C" };
     /** 计算器左边的M的显示名字 */
     private final String[] M = {"MC", "MR", "MS", "M+" };
     /** 计算器上键的按钮 */
     private JButton keys[] = new JButton[KEYS.length];
     /** 计算器上的功能键的按钮 */
     private JButton commands[] = new JButton[COMMAND.length];
     /** 计算器左边的M的按钮 */
     private JButton m[] = new JButton[M.length];
     /** 计算结果文本框 */
     private JTextField resultText = new JTextField("");
 
     // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
     private boolean firstDigit = true;
     // 计算的中间结果。
     private double resultNum = 0.0;
     // 当前运算的运算符
     private String operator = "=";
     // 操作是否合法
     private boolean operateValidFlag = true;
 
 StringBuffer sb = new StringBuffer();  // 存储输入的数字
 String vl = null;  // 存储事件点击的字符
 
   //标准型计算器
 public Calculate_Ordinary() {
  setTitle("标准型");  
         // 初始化计算器 super p114
      
         // 设置计算器的背景颜色
         this.setBackground(Color.LIGHT_GRAY);
         this.setTitle("标准计算器");
         // 在屏幕(500, 300)坐标处显示计算器
         this.setLocation(500, 300);
         // 不许修改计算器的大小
         this.setSize(500, 500);
         this.setResizable(false);
         // 使计算器中各组件大小合适
         //使这个外部容器自动调整成刚好装下这几个按钮的大小的尺寸。
    
  JMenuBar menuBar = new JMenuBar();
  setJMenuBar(menuBar);
  JMenu menu = new JMenu("功能");
  menuBar.add(menu);
  
  JMenuItem menuItem = new JMenuItem("标准型");
  menu.add(menuItem);
  JMenuItem menuItem_1 = new JMenuItem("科学型");
  menu.add(menuItem_1);
  menuItem_1.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
    Calculator_Science science = new Calculator_Science();
    science.setVisible(true);
   }
  });
     
  
 
        
         // 设置文本框背景颜色为白色
         resultText.setBackground(Color.white);
         resultText.setFont(new Font("TimesRoman",Font.BOLD,46));
        
 
         // 初始化计算器上键的按钮，将键放在一个画板内
         JPanel calckeysPanel = new JPanel();
         // 用网格布局器，4行，5列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
         calckeysPanel.setLayout(new GridLayout(4, 5, 3, 3));
         for (int i = 0; i < KEYS.length; i++) {
             keys[i] = new JButton(KEYS[i]);
             calckeysPanel.add(keys[i]);
             keys[i].setForeground(Color.white);
             keys[i].setBackground(Color.DARK_GRAY);
             keys[i].setFont(new Font("Courir",Font.ITALIC,20));
         }
        
          
        
         // 运算符键用红色标示，其他键用黑色表示
         keys[3].setForeground(Color.white);
         keys[8].setForeground(Color.white);
         keys[13].setForeground(Color.white);
         keys[18].setForeground(Color.white);
         keys[19].setForeground(Color.white);
         keys[3].setBackground(Color.getHSBColor(250, 155, 140));
         keys[8].setBackground(Color.getHSBColor(250, 155, 140));
         keys[13].setBackground(Color.getHSBColor(250, 155, 140));
         keys[18].setBackground(Color.getHSBColor(250, 155, 140));
         keys[19].setBackground(Color.getHSBColor(250, 155, 140));              
         // 初始化功能键，都用红色标示。将功能键放在一个画板内
         JPanel commandsPanel = new JPanel();
         // 用网格布局器，1行，3列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
         commandsPanel.setLayout(new GridLayout(1, 3, 3, 3));
         for (int i = 0; i < COMMAND.length; i++) {
             commands[i] = new JButton(COMMAND[i]);
             commandsPanel.add(commands[i]);
             commands[i].setForeground(Color.DARK_GRAY);
             commands[i].setFont(new Font("Courir",Font.PLAIN,20));
            
         }
 
         // 初始化M键，用红色标示，将M键放在一个画板内
         JPanel calmsPanel = new JPanel();
         // 用网格布局管理器，5行，1列的网格，网格之间的水平方向间隔为3个象素，垂直方向间隔为3个象素
         calmsPanel.setLayout(new GridLayout(4, 1, 3, 3));
         for (int i = 0; i < M.length; i++) {
             m[i] = new JButton(M[i]);
             calmsPanel.add(m[i]);
             m[i].setForeground(Color.red);
             m[i].setFont(new Font("Courir",Font.PLAIN,20));
            
         }
 
         // 下面进行计算器的整体布局，将calckeys和command画板放在计算器的中部，
         // 将文本框放在北部，将calms画板放在计算器的西部。
 
         // 新建一个大的画板，将上面建立的command和calckeys画板放在该画板内
         JPanel panel1 = new JPanel();
         // 画板采用边界布局管理器，画板里组件之间的水平和垂直方向上间隔都为3象素
         panel1.setLayout(new BorderLayout(3, 3));
         panel1.add("North", commandsPanel);
         panel1.add("Center", calckeysPanel);
 
         // 建立一个画板放文本框
         JPanel top = new JPanel();
         top.setLayout(new BorderLayout());
         top.add("Center", resultText);
        
 
         // 整体布局
         getContentPane().setLayout(new BorderLayout(3, 5));
         getContentPane().add("North", top);
         getContentPane().add("Center", panel1);
         getContentPane().add("West", calmsPanel);
         // 为各按钮添加事件侦听器
         // 都使用同一个事件侦听器，即本对象。本类的声明中有implements ActionListener
         for (int i = 0; i < KEYS.length; i++) {
             keys[i].addActionListener(this);
         }
         for (int i = 0; i < COMMAND.length; i++) {
             commands[i].addActionListener(this);
         }
         for (int i = 0; i < M.length; i++) {
             m[i].addActionListener(this);
            
         }
    
     }
 
 
 //添加事件点击
 public void actionPerformed(ActionEvent e) {
        // 获取事件源的标签
        String label = e.getActionCommand();
        if (label.equals(COMMAND[0])) {
            // 用户按了"Backspace"键
            handleBackspace();
        } else if (label.equals(COMMAND[1])) {
            // 用户按了"CE"键
            resultText.setText("0");
        } else if (label.equals(COMMAND[2])) {
            // 用户按了"C"键
            handleC();
        } else if ("0123456789.".indexOf(label) >= 0) {
            // 用户按了数字键或者小数点键
            handleNumber(label);
            // handlezero(zero);
        } else {
            // 用户按了运算符键
            handleOperator(label);
        }
    }
 
    /**
     * 处理Backspace键被按下的事件
     */
    private void handleBackspace() {
        String text = resultText.getText();
        int i = text.length();
        if (i > 0) {
            // 退格，将文本最后一个字符去掉
            text = text.substring(0, i - 1);
            if (text.length() == 0) {
                // 如果文本没有了内容，则初始化计算器的各种值
                resultText.setText("0");
                firstDigit = true;
                operator = "=";
            } else {
                // 显示新的文本
                resultText.setText(text);
            }
        }
    }
 
    /**
     * 处理数字键被按下的事件
     *
     * @param key
     */
    private void handleNumber(String key) {
        if (firstDigit) {
            // 输入的第一个数字
            resultText.setText(key);
        } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
            // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
            resultText.setText(resultText.getText() + ".");
        } else if (!key.equals(".")) {
            // 如果输入的不是小数点，则将数字附在结果文本框的后面
            resultText.setText(resultText.getText() + key);
        }
        // 以后输入的肯定不是第一个数字了
        firstDigit = false;
    }
 
    /**
     * 处理C键被按下的事件
     */
    private void handleC() {
        // 初始化计算器的各种值
        resultText.setText("0");
        firstDigit = true;
        operator = "=";
    }
 
    /**
     * 处理运算符键被按下的事件
     *
     * @param key
     */
    private void handleOperator(String key) {
        if (operator.equals("/")) {
         
            // 除法运算
            // 如果当前结果文本框中的值等于0
            if (getNumberFromText() == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("除数不能为零");
            } else {
                resultNum /= getNumberFromText();
            }
        } else if (operator.equals("1/x")) {
            // 倒数运算
            if (resultNum == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                resultText.setText("零没有倒数");
            } else {
                resultNum = 1 / resultNum;
            }
        } else if (operator.equals("+")) {
            // 加法运算
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            // 减法运算
            resultNum -= getNumberFromText();
        } else if (operator.equals("*")) {
            // 乘法运算
            resultNum *= getNumberFromText();
        } else if (operator.equals("√")) {
            // 平方根运算
            resultNum = Math.sqrt(resultNum);
        } else if (operator.equals("%")) {
            // 百分号运算，除以100
            resultNum = resultNum / 100;
        } else if (operator.equals("+/-")) {
            // 正数负数运算
            resultNum = resultNum * (-1);
        } else if (operator.equals("=")) {
            // 赋值运算
            resultNum = getNumberFromText();
        }
        if (operateValidFlag) {
            // 双精度浮点数的运算
            long t1;
            double t2;
            t1 = (long) resultNum;
            t2 = resultNum - t1;
            if (t2 == 0) {
                resultText.setText(String.valueOf(t1));
            } else {
                resultText.setText(String.valueOf(resultNum));
            }
        }
        // 运算符等于用户按的按钮
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }
 
    /**
     * 从结果文本框中获取数字
     *
     * @return
     */
    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(resultText.getText()).doubleValue();
        } catch (NumberFormatException e) {
        }
        return result;
    }
 
    public static void main(String args[]) {
        Calculate_Ordinary calculator1 = new Calculate_Ordinary();
        calculator1.setVisible(true);
        calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

