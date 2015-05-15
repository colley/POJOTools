/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.tools.generator.util.PropertiesUtils;
import com.ai.tools.generator.util.StringPool;
import com.ai.tools.generator.util.StringUtil;
import com.ai.tools.generator.util.TimeUtil;
import com.ai.tools.generator.util.XMLFormatter;


/**
 * mvn assembly:assembly
 * @User: mayc
 * @Date: 2014年4月30日
 * @Time: 下午4:28:53
 * @version $Revision$ $Date$
 */
public class POJOTools {
	 Log log = LogFactory.getLog(this.getClass().getName());
     JFrame frame = new JFrame("POJO Builder Tool");
     JLabel lblformat = new JLabel("                    ");

    //类型
     JLabel    JLTypeLabel = new JLabel("TYPE :");
     JComboBox POJOComType = new JComboBox(new String[] {
                "JSON"
            });
     JLabel     lblRootNode = new JLabel("POJO Name :");
     JTextField txtRootNode = new JTextField(PropertiesUtils.getValue("POJODefault.name"), 40);
    
    //父类
     JLabel     lblPNode = new JLabel("Parent Class :");
     JTextField txtPNode = new JTextField(PropertiesUtils.getValue("Default.parentClass"), 40);
     
    /*static JLabel lblFileName = new JLabel("XML Path :");
    static JTextField txtFileName = new JTextField("", 40);*/
     JLabel     lblPackageName = new JLabel("Package Name :");
     JTextField txtPackageName = new JTextField(PropertiesUtils.getValue("Default.PackageName"), 40);

    //工程目录
     JLabel     lblSaveDir = new JLabel("Project Path :");
     JTextField txtSaveDir = new JTextField(System.getProperty("user.dir") +
            System.getProperty("file.separator") + "src", 40);
     JButton btnSave = new JButton("SaveToXML");
     JButton btnParse = new JButton("JSON Parse");
     JButton btnXsdToJava = new JButton("JSonToJava");
     String XmlfilePath = "";
     boolean isFrom = Boolean.FALSE;
     JTextArea   ta = new JTextArea();
     JScrollPane jsp = new JScrollPane(ta);

    public  void startFrame() {
        frame.getContentPane().setLayout(new BorderLayout());
        //CBossLogger.set
        JPanel ap = new JPanel();
        ap.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        ap.add(JLTypeLabel, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        ap.add(POJOComType, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        ap.add(lblRootNode, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        ap.add(txtRootNode, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        ap.add(lblPNode, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        ap.add(txtPNode, gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        ap.add(lblPackageName, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        ap.add(txtPackageName, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        ap.add(lblSaveDir, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        ap.add(txtSaveDir, gbc);

        JPanel p1 = new JPanel();
        p1.add(btnParse);
        p1.add(lblformat);
        p1.add(btnSave);
        p1.add(btnXsdToJava);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        ap.add(p1, gbc);

        frame.getContentPane().add(ap, BorderLayout.NORTH);
        frame.getContentPane().add(jsp);

        btnParse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String currentTime = TimeUtil.getSimpleDate(new Date(), "yyyy");
                	if(Long.valueOf(currentTime)>=StringPool.EXPIRE_DATE){
                		JOptionPane.showMessageDialog(null, "工具已经失效，请联系开发人员！");
                		return;
                	}
                    if (POJOComType.getSelectedItem().equals("JSON")) {
                        String content = ta.getText();
                        if (StringUtil.isNotEmpty(content)) {
                            try {
                            	if(content.startsWith("[")){
                            		JSONArray jsonArray = JSONArray.fromObject(content);
                            		content = jsonArray.getJSONObject(0).toString();
                            	}else if(content.startsWith("{")){
                            		 JSONObject.fromObject(content);
                            	}else{
                            		//不是正确的json数据
                            		String[] contentArray = content.split("\n");
                            		if(contentArray!=null && contentArray.length>0){
                            			Map<String,String> jsonMap = new HashMap<String,String>();
                            			for(String contStr :contentArray){
                            				String[] contStrArr = contStr.split(" ");
                            				String lastCont = contStrArr[contStrArr.length-1].trim();
                            				if(lastCont.endsWith(";")){
                            					lastCont = lastCont.substring(0,lastCont.length()-1);
                            				}
                            				jsonMap.put(lastCont, "");
                            			}
                            			content = JSONObject.fromObject(jsonMap).toString();
                            		}
                            	}
                            } catch (Exception e2) {
                            	log.error(e2);
                                JOptionPane.showMessageDialog(null, "您输入的JSON格式不合法！");
                            }

                            if (StringUtil.isEmpty(txtPackageName.getText())) {
                                JOptionPane.showMessageDialog(null, "包名称不能为空！");
                                return;
                            }

                            //处理逻辑
                            if (StringUtil.isEmpty(txtSaveDir.getText())) {
                                JOptionPane.showMessageDialog(null, "工程路径不能为空！");
                                return;
                            }

                            if (StringUtil.isEmpty(txtRootNode.getText())) {
                                JOptionPane.showMessageDialog(null, "POJO Name 不能为空，并且第一个字母大写！");
                                return;
                            }

                            String         implDir = txtSaveDir.getText().trim();
                            
                            JSON2EntityXml jsonXml = new JSON2EntityXml(implDir,
                                    txtPackageName.getText().trim(),txtPNode.getText().trim(), 1);
                            try {
                                String xmlPath = jsonXml.createXML(content, txtRootNode.getText());
                                XmlfilePath = xmlPath;
                                isFrom = Boolean.FALSE;
                                String oldContent = FileUtils.readFileToString(new File(xmlPath));
                                ta.setText(oldContent);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                log.error(e1);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "JSON 数据不能为空！");
                        }
                    }
                }
            });

        btnSave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    save();
                }
            });

        btnXsdToJava.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    xsdToJava();
                }
            });

        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.dispose();
                    System.exit(0);
                }
            });
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public  void xsdToJava() {
    	String currentTime = TimeUtil.getSimpleDate(new Date(), "yyyy");
    	if(Long.valueOf(currentTime)>=StringPool.EXPIRE_DATE){
    		JOptionPane.showMessageDialog(null, "工具已经失效，请联系开发人员！");
    		return;
    	}
        if (StringUtil.isNotEmpty(XmlfilePath)) {
            String projectpath = txtSaveDir.getText();
            if (StringUtil.isEmpty(projectpath)) {
                JOptionPane.showMessageDialog(null, "Project Path 不能为空！");
                return;
            }
            String[] params = new String[2];
            params[0] = XmlfilePath;
            params[1] = projectpath.trim();
            try {
                POJOBuilder.process(params);
                JOptionPane.showMessageDialog(null, "POJO类成功生成！");
                System.gc();
                new File(XmlfilePath).deleteOnExit();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e);
                JOptionPane.showMessageDialog(null, "Error! 请查看logs.log日志");
            }
        } else {
            JOptionPane.showMessageDialog(null, "请点击【JSON-JSON parse】或者【XML-SaveToXml】");
        }
    }

    public  void save() {
    	String currentTime = TimeUtil.getSimpleDate(new Date(), "yyyy");
    	if(Long.valueOf(currentTime)>=StringPool.EXPIRE_DATE){
    		JOptionPane.showMessageDialog(null, "工具已经失效，请联系开发人员！");
    		return;
    	}
        String content = ta.getText();
        System.err.println(content);
        if(StringUtil.isEmpty(XmlfilePath) || isFrom){
        	String packpath = txtPackageName.getText();
        	XmlfilePath = txtSaveDir.getText()+File.separator+StringUtil.replace(packpath, ".", "/")+File.separator+txtRootNode.getText()+".xml";
        	isFrom = Boolean.TRUE;
        }
        if (StringUtil.isNotEmpty(content) && StringUtil.isNotEmpty(XmlfilePath)) {
            String newContent = "";
            try {
            	//content = new String (content.getBytes("GBK"));
            	//content = new String(content.getBytes(StringPool.UTF8));
                newContent = XMLFormatter.formatXML(content);
            } catch (Exception e) {
            	 System.err.println(e.getMessage());
            	 e.printStackTrace();
            	 log.error(e);
                JOptionPane.showMessageDialog(null, "XML格式非法！");
                return;
            }

           
            try {
                FileUtils.writeStringToFile(new File(XmlfilePath), newContent,StringPool.UTF8);
                JOptionPane.showMessageDialog(null, "Save Success !");
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e);
                JOptionPane.showMessageDialog(null, "Save Error! 请查看logs.log日志");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Save Error! 请查看logs.log日志");
        }
    }

    public static void main(String[] args) {
        new POJOTools().startFrame();
    	//String name = "com.test";
    	//System.out.println(StringUtil.replace(name, ".", "/"));
    }
}
