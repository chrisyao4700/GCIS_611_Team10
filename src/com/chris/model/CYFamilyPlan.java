package com.chris.model;

import java.util.ArrayList;
import java.util.HashMap;

public class CYFamilyPlan {
	private String groupName;
	private String leaderNumber;
	private String dataSelection;
	private String memberWithContract;
	private String memberWithoutContract;
	private String totalMember;
	private String totalBalance;
	private String averageBalance;

	private int totalNum;
	private int withConNum;
	private int noConNum;
	private float totalBalNum;
	private float averageBalNum;
	
	private ArrayList<String> contentArray;
	private HashMap<String,String> contentHashMap;

	public CYFamilyPlan(String _groupName, String _leaderNumber,
			String _dataSelection, String _memberWithContract,
			String _memberWithoutContract) {
		this.groupName = _groupName;
		this.leaderNumber = _leaderNumber;
		this.dataSelection = _dataSelection;
		this.memberWithContract = _memberWithContract;
		this.memberWithoutContract = _memberWithoutContract;
		this.calTotalNumber();
		this.calTotalBalance();
		this.setContentArray();
		this.setContentDictionary();
	}
	
	public CYFamilyPlan(HashMap<String,String> _contentDictionary){
		this.contentHashMap = _contentDictionary;
		this.setContentArrayFromDictionary();
		this.setElementsFromContentArray();
	}
	
	public CYFamilyPlan(ArrayList<String> _contentArray){
		this.contentArray = _contentArray;
		this.setElementsFromContentArray();
		this.setContentDictionary();
		
	}
	
	private void setContentDictionary(){
		this.contentHashMap = new HashMap<String,String>();
		ArrayList<String> keys = CYFamilyPlan.getColumnNames();
		for(int i = 0; i<8;i++){
			contentHashMap.put(keys.get(i),this.contentArray.get(i));
		}
	}
	private void setContentArrayFromDictionary(){
		contentArray = new ArrayList<String>();
		ArrayList<String> keys = CYFamilyPlan.getColumnNames();
		for(int i =0; i<8;i++){
			contentArray.add(this.contentHashMap.get(keys.get(i)));
		}
		//System.out.println(contentArray);
	}
	private void setElementsFromContentArray(){
		this.groupName = this.contentArray.get(0);
		this.leaderNumber = this.contentArray.get(1);
		this.dataSelection = this.contentArray.get(2);
		this.memberWithContract = this.contentArray.get(3);
		this.memberWithoutContract = this.contentArray.get(4);
		this.totalMember = this.contentArray.get(5);
		this.totalBalance = this.contentArray.get(6);
		this.averageBalance = this.contentArray.get(7);
	}

	private void calTotalNumber() {
		this.withConNum = (int) Double.parseDouble(memberWithContract);
		this.noConNum = (int) Double.parseDouble(memberWithoutContract);
		this.totalNum = withConNum + noConNum;

		totalMember = "" + totalNum;
	}
	private void calTotalBalance(){
		if(this.dataSelection.equals("300MB")){
			this.cal(20, 40, 25);
		}else if(this.dataSelection.equals("1GB")){
			this.cal(25, 40, 25);
		}else if(this.dataSelection.equals("2GB")){
			this.cal(40, 40, 25);
		}else if(this.dataSelection.equals("4GB")){
			this.cal(70, 40, 25);
		}else if(this.dataSelection.equals("6GB")){
			this.cal(80, 40, 25);
		}else if(this.dataSelection.equals("10GB")){
			this.cal(100, 40, 15);
		}
	}
	private void cal(int base, int with_extra, int no_extra ){	
		this.totalBalNum = base + (withConNum * with_extra) + (noConNum * no_extra);
		this.averageBalNum = this.totalBalNum/this.totalNum;
		
		this.totalBalance = "" + this.totalBalNum;
		this.averageBalance= "" + this.averageBalNum;
	}
	public static ArrayList<String> getColumnNames(){
		String[] nameList = new String[8];
		nameList[0] = "Group_Name";
		nameList[1] = "Leader_Number";
		nameList[2] = "Data_Selection";
		nameList[3] = "Member_With_Contract";
		nameList[4] = "Member_Without_Contract";
		nameList[5] = "Total_Members";
		nameList[6] = "Total_Balance";
		nameList[7] = "Average_Balance";
		
		ArrayList<String> nameArray = new ArrayList<String>();
		for(int i = 0; i<8;i++){
			nameArray.add(nameList[i]);
		}
		return nameArray;
		
	}
	public static String[] getStringColumnNames(){
		ArrayList<String> keys = CYFamilyPlan.getColumnNames();
		String[] stringKeys = new String[8];
		for(int i =0;i<8;i++){
			stringKeys[i] = keys.get(i);
			}
		return stringKeys;
	}
	
	
	public String[] getStringContent(){
		String[] stringContent= new String[8];
		for(int i = 0 ; i<8; i++){
			//System.out.println(contentArray.get(i));
			stringContent[i] = contentArray.get(i);
			//System.out.printf("%d:%s    ",i,stringContent[i]);
		}
		return stringContent;
			
	}
	
	private void setContentArray(){
	contentArray = new ArrayList<String>();
	
	contentArray.add(this.groupName);
	contentArray.add(this.leaderNumber);
	contentArray.add(this.dataSelection);
	contentArray.add(this.memberWithContract);
	contentArray.add(this.memberWithoutContract);
	contentArray.add(this.totalMember);
	contentArray.add(this.totalBalance);
	contentArray.add(this.averageBalance);	
	}
	
	public ArrayList<String> getContentArray(){
		return contentArray;
	}
	public String getGroupName(){
		return groupName;
	}
	public HashMap<String,String> getContentHashMap(){
		return contentHashMap;
	}

}
