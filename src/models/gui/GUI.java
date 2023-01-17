package models.gui;

import models.FileOperations.CVSFileOperation;
import models.FileOperations.JSONFileOperation;
import models.Objects.Stopky;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame {

    JLabel lblTime = new JLabel();
    JTable jt;
    boolean TimerRunning = false;
    JSONFileOperation json = new JSONFileOperation();
    CVSFileOperation cvs = new CVSFileOperation();
    Stopky stopky = new Stopky();
    ArrayList<Stopky> timeValues;
    String column[]= {"ID","TIME"};

    public GUI(int width, int height) {
        super("Project PRO2");
        stopky.setMiliseconds(0);
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initGui();
        setVisible(true);
    }

    public void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.add(initTimerPanel(), BorderLayout.NORTH);
        panelMain.add(initButtonPanel(),BorderLayout.CENTER);
        panelMain.add(initTablePanel(),BorderLayout.SOUTH);
        add(panelMain);
    }

    public JPanel initTimerPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblTime.setFont(new Font("Arial", Font.PLAIN, 30));
        lblTime.setForeground(Color.BLACK);
        lblTime.setText("0:0:0:0");

        //FIXME MULTITHREADING
        Runnable runTimer = () ->{
            Thread.currentThread().setName("runTimer");
            try{
                while (true) {
                    if(TimerRunning)
                    {
                        setTime();
                        stopky.setMiliseconds(stopky.getMiliseconds()+2);
                        lblTime.setText(stopky.toString());
                    }
                    Thread.sleep(1);
                }
            }catch(Exception e){}
        };
        Thread refreshDataThread = new Thread(runTimer);
        refreshDataThread.start();


        panel.add(lblTime);

        return panel;
    }

    public void setTime(){
        if(stopky.getMiliseconds() == 1000)
        {
            stopky.setSeconds(stopky.getSeconds()+1);
            stopky.setMiliseconds(0);
        }
        if(stopky.getSeconds() == 60)
        {
            stopky.setMinutes(stopky.getMinutes()+1);
            stopky.setSeconds(0);
        }
    }

    public JPanel initButtonPanel(){
        timeValues = new ArrayList<>();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnStart = new JButton("Start Timer");
        JButton btnEndTime = new JButton("STOP TIME");
        btnEndTime.setEnabled(false);
        JButton btnSaveTime = new JButton("Save intermediate time");
        btnSaveTime.setEnabled(false);
        JButton btnSaveFile = new JButton("Save into file");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TimerRunning == true)
                {
                    btnStart.setText("Resume timer");
                    TimerRunning = false;
                }
                else {
                    btnStart.setText("Pause timer");
                    TimerRunning = true;
                }
                btnSaveTime.setEnabled(true);
                btnEndTime.setEnabled(true);
            }
        });

        btnSaveTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeValues.add(new Stopky(stopky));
                UpdateTable();
            }
        });

        btnEndTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeValues.add(stopky);
                stopky = new Stopky();

                TimerRunning = false;

                btnStart.setText("Start Timer");
                lblTime.setText("0:0:0:0");
                btnSaveTime.setEnabled(false);
                btnEndTime.setEnabled(false);
                UpdateTable();
            }
        });

        btnSaveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(btnStart);
        panel.add(btnSaveTime);
        panel.add(btnEndTime);
        panel.add(btnSaveFile);

        return panel;
    }

    public JPanel initTablePanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        DefaultTableModel model = new DefaultTableModel(column, 0);
        jt=new JTable(model);
        jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);
        panel.add(sp);

        return panel;
    }

    public void UpdateTable()
    {
        DefaultTableModel tableModel = new DefaultTableModel(column,0);
        for(int i = 0; i< timeValues.size();i++)
        {
            tableModel.insertRow(i,new Object[] {i+1,timeValues.get(i)});
        }
        jt.setModel(tableModel);
    }

    public void save() throws IOException, ParseException {
        ArrayList<String> c = new ArrayList<>();
        c.add("ID");
        c.add("TIME");
        json.Save(/*new ArrayList<>(),*/timeValues);
//        cvs.save(timeValues);
//        cvs.load();
    }

    public void loadTable() throws IOException, ParseException {
        timeValues = new ArrayList<>();

        ArrayList<String> loadedValues = json.Load();

        for(int i = 0;i< loadedValues.size();i++)
        {
            stopky = new Stopky();
            String[] values = loadedValues.get(i).split(":");
            stopky.setHour(Integer.parseInt(values[0]));
            stopky.setMinutes(Integer.parseInt(values[1]));
            stopky.setSeconds(Integer.parseInt(values[2]));
            stopky.setMiliseconds(Integer.parseInt(values[3]));

            timeValues.add(stopky);
        }
        UpdateTable();
    }
}
