package com.demo.dao;

import java.util.List;

import com.demo.bean.LeaveBean;
import com.demo.model.Leave;

public interface LeaveDaoInt {
	
	String leaveRequest(LeaveBean leave);
	String updateLeaveRequest(LeaveBean leave);
	List<Leave> leaveRequests(String email);
	List<Leave> leaveRequests();
	Leave getLeave(Long leaveID);
	Boolean isTechnicianOnLeave(String technicianEmail);
	String onLeaveTechnician(String technicianEmail);
	String [] techniciansOnLeave();
	void isTechnicianOnLeaveDate();
	
	List<Leave> getPendingLeaves();
	List<Leave> getApprovedLeave();
	List<Leave> getActiveLeave();
	List<Leave> getCancelledLeave();
	List<Leave> leaveHistory();	
		
	int countAllPendingLeave();
	int countAllActiveLeave();
	int countAllApprovedLeave();
	int countAllCancelledLeave();
	
	int countpendingLeaveForSelectedDate(String dateRange);
	int countActiveLeaveForSelectedDate(String dateRange);
	int countApprovedLeaveForSelectedDate(String dateRange);
	int countCancelledLeaveForSelectedDate(String dateRange);
	
	int countTechpendingLeaveForSelectedDate(String dateRange, String technicianEmail);
	int countTechApprovedLeaveForSelectedDate(String dateRange, String technicianEmail);
	int countTechActiveLeaveForSelectedDate(String dateRange,String technicianEmail);	
	int countTechCancelledLeaveForSelectedDate(String dateRange,String technicianEmail);
	
	int getPendingLeaveCountByTechnician(String technicianEmail);
	int getApprovedLeaveCountByTechnician(String technicianEmail);
	int getActiveLeaveCountByTechnician(String technicianEmail);	
	int getCancelledLeaveCountByTechnician(String technicianEmail);
	
	List<Leave> getPendingLeaveByTechnician(String technicianEmail);	
	List<Leave> getApprovedLeaveByTechnician(String technicianEmail);
	List<Leave> getActiveLeaveByTechnician(String technicianEmail);
	List<Leave> getCancelledLeaveByTechnician(String technicianEmail);	
	List<Leave> getLeaveHistoryByTechician(String technicianEmail);
	
	List<Leave> getAllActiveAndPendingLeave();	
	List<Leave> activeAndPendingLeaveByTechnician(String technicianEmail);
	
	List<Leave> getAllPendingActiveApprovedAndCancelledLeave();	
	List<Leave> pendingActiveApprovedAndCancelledLeaveByTechnician(String technicianEmail);
		
	List<String> getLeaveDates();
	
	List<Leave> getAllLeaveForSelectedRange(String dateRange);	
	List<Leave> getPendingLeaveForSelectedRange(String dateRange);
	List<Leave> getActiveLeaveForSelectedRange(String dateRange);	
	List<Leave> getApprovedLeaveForSelectedRange(String dateRange);
	List<Leave> getCancelledLeaveForSelectedRange(String dateRange);
	List<Leave> getLeaveHistoryForSelectedRange(String dateRange);
	
	List<Leave> getAllTechLeaveForSelectedRange(String dateRange,String technicianEmail);
	List<Leave> getTechPendingLeaveForSelectedRange(String dateRange,String technicianEmail);
	List<Leave> getTechActiveLeaveForSelectedRange(String dateRange,String technicianEmail);
	List<Leave> getTechApprovedLeaveForSelectedRange(String dateRange,String technicianEmail);
	List<Leave> getTechCancelledLeaveForSelectedRange(String dateRange,String technicianEmail);
	List<Leave> getTechLeaveHistoryForSelectedRange(String dateRange,String technicianEmail);
	
	void scheduledLeaveStatus();
	String cancelLeave(Long leaveID);
	String approveLeave(Long leaveID);
	String declineLeave(Long LeaveID,String reasonDeclined);	
	
}
