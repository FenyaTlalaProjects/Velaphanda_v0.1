package com.demo.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.LeaveBean;
import com.demo.dao.LeaveDaoInt;
import com.demo.model.Leave;
import com.demo.service.LeaveInt;


@Service("leaveService")
@Transactional
public class LeaveService implements LeaveInt{
	
	@Autowired
	private LeaveDaoInt leaveDaoInt;
	
	private String retMessage = null;

	@Override
	public String leaveRequest(LeaveBean leave) {
		retMessage = leaveDaoInt.leaveRequest(leave);
		return retMessage;
	}

	@Override
	public String updateLeaveRequest(LeaveBean leave) {
		retMessage = leaveDaoInt.updateLeaveRequest(leave);
		return retMessage;
	}

	@Override
	public List<Leave> leaveRequests(String email) {
		
		return leaveDaoInt.leaveRequests(email);
	}

	@Override
	public List<Leave> leaveRequests() {
		
		return leaveDaoInt.leaveRequests();
	}

	@Override
	public Boolean isTechnicianOnLeave(String technicianEmail) {
		return leaveDaoInt.isTechnicianOnLeave(technicianEmail);
	}

	@Override
	public String onLeaveTechnician(String technicianEmail) {
		return leaveDaoInt.onLeaveTechnician(technicianEmail);
	}

	@Override
	public String[] techniciansOnLeave() {
		return leaveDaoInt.techniciansOnLeave();
	}

	@Override
	public int countAllPendingLeave() {
		return leaveDaoInt.countAllPendingLeave();
	}

	@Override
	public int countAllActiveLeave() {
		return leaveDaoInt.countAllActiveLeave();
	}

	@Override
	public List<Leave> getPendingLeaves() {
	
		return leaveDaoInt.getPendingLeaves();
	}

	@Override
	public List<Leave> getActiveLeave() {
			return leaveDaoInt.getActiveLeave();
	}

	@Override
	public List<Leave> leaveHistory() {
			return leaveDaoInt.leaveHistory();
	}

	@Override
	public List<Leave> getPendingLeaveByTechnician(String technicianEmail) {
		    return leaveDaoInt.getPendingLeaveByTechnician(technicianEmail);
	}

	@Override
	public int getPendingLeaveCountByTechnician(String technicianEmail) {
			return leaveDaoInt.getPendingLeaveCountByTechnician(technicianEmail);
	}

	@Override
	public int getActiveLeaveCountByTechnician(String technicianEmail) {
		return leaveDaoInt.getActiveLeaveCountByTechnician(technicianEmail);
	}

	@Override
	public List<Leave> getActiveLeaveByTechnician(String technicianEmail) {
		return leaveDaoInt.getActiveLeaveByTechnician(technicianEmail);
	}

	@Override
	public List<Leave> getAllActiveAndPendingLeave() {
		return leaveDaoInt.getAllActiveAndPendingLeave();
	}

	@Override
	public List<Leave> activeAndPendingLeaveByTechnician(String technicianEmail) {
		return leaveDaoInt.activeAndPendingLeaveByTechnician(technicianEmail);
	}

	@Override
	public List<Leave> getLeaveHistoryByTechician(String technicianEmail) {
		return leaveDaoInt.getLeaveHistoryByTechician(technicianEmail);
	}

	@Override
	public int countpendingLeaveForSelectedDate(String dateRange) {
		return leaveDaoInt.countpendingLeaveForSelectedDate(dateRange);
	}

	@Override
	public List<String> getLeaveDates() {
		return leaveDaoInt.getLeaveDates();
	}

	@Override
	public List<Leave> getAllLeaveForSelectedRange(String dateRange) {
		return leaveDaoInt.getAllLeaveForSelectedRange(dateRange);
	}

	@Override
	public int countActiveLeaveForSelectedDate(String dateRange) {
		return leaveDaoInt.countActiveLeaveForSelectedDate(dateRange);
	}

	@Override
	public List<Leave> getPendingLeaveForSelectedRange(String dateRange) {
		return leaveDaoInt.getPendingLeaveForSelectedRange(dateRange);
	}

	@Override
	public List<Leave> getActiveLeaveForSelectedRange(String dateRange) {
		return leaveDaoInt.getActiveLeaveForSelectedRange(dateRange);
	}

	@Override
	public List<Leave> getLeaveHistoryForSelectedRange(String dateRange) {
		return leaveDaoInt.getLeaveHistoryForSelectedRange(dateRange);
	}

	@Override
	public int countTechpendingLeaveForSelectedDate(String dateRange,
			String technicianEmail) {
		return leaveDaoInt.countTechpendingLeaveForSelectedDate(dateRange, technicianEmail);
	}

	@Override
	public int countTechActiveLeaveForSelectedDate(String dateRange,
			String technicianEmail) {
		return leaveDaoInt.countTechActiveLeaveForSelectedDate(dateRange, technicianEmail);
	}

	@Override
	public List<Leave> getAllTechLeaveForSelectedRange(String dateRange,
			String technicianEmail) {
		return leaveDaoInt.getAllTechLeaveForSelectedRange(dateRange, technicianEmail);
	}

	@Override
	public List<Leave> getTechPendingLeaveForSelectedRange(String dateRange,
			String technicianEmail) {
		return leaveDaoInt.getTechPendingLeaveForSelectedRange(dateRange, technicianEmail);
	}

	@Override
	public List<Leave> getTechActiveLeaveForSelectedRange(String dateRange,
			String technicianEmail) {
		return leaveDaoInt.getTechActiveLeaveForSelectedRange(dateRange, technicianEmail);
	}

	@Override
	public List<Leave> getTechLeaveHistoryForSelectedRange(String dateRange,
			String technicianEmail) {
		return leaveDaoInt.getTechLeaveHistoryForSelectedRange(dateRange, technicianEmail);
	}

	@Override
	public String cancelLeave(Long leaveID) {
		return leaveDaoInt.cancelLeave(leaveID);
	}

	@Override
	public Leave getLeave(Long leaveID) {
		return leaveDaoInt.getLeave(leaveID);
	}

	@Override
	public String approveLeave(Long leaveID) {
		return leaveDaoInt.approveLeave(leaveID);
	}

	@Override
	public String declineLeave(Long LeaveID,String reasonDeclined) {
		return leaveDaoInt.declineLeave(LeaveID,reasonDeclined);
	}

	@Override
	public List<Leave> getApprovedLeave() {	
		return leaveDaoInt.getApprovedLeave();
	}

	@Override
	public List<Leave> getCancelledLeave() {
		
		return leaveDaoInt.getCancelledLeave();
	}

	@Override
	public List<Leave> getApprovedLeaveByTechnician(String technicianEmail) {
		
		return leaveDaoInt.getApprovedLeaveByTechnician(technicianEmail);
	}

	@Override
	public List<Leave> getCancelledLeaveByTechnician(String technicianEmail) {
		
		return leaveDaoInt.getCancelledLeaveByTechnician(technicianEmail);
	}

	@Override
	public List<Leave> getApprovedLeaveForSelectedRange(String dateRange) {
		
		return leaveDaoInt.getApprovedLeaveForSelectedRange(dateRange);
	}

	@Override
	public List<Leave> getCancelledLeaveForSelectedRange(String dateRange) {
		
		return leaveDaoInt.getCancelledLeaveForSelectedRange(dateRange);
	}

	@Override
	public int countAllApprovedLeave() {
		
		return leaveDaoInt.countAllApprovedLeave();
	}

	@Override
	public int countAllCancelledLeave() {
		
		return leaveDaoInt.countAllCancelledLeave();
	}

	@Override
	public int countApprovedLeaveForSelectedDate(String dateRange) {
		return leaveDaoInt.countApprovedLeaveForSelectedDate(dateRange);
	}

	@Override
	public int countCancelledLeaveForSelectedDate(String dateRange) {
		return leaveDaoInt.countCancelledLeaveForSelectedDate(dateRange);
	}

	@Override
	public int countTechApprovedLeaveForSelectedDate(String dateRange,
			String technicianEmail) {		
		return leaveDaoInt.countTechApprovedLeaveForSelectedDate(dateRange,technicianEmail);
	}

	@Override
	public int countTechCancelledLeaveForSelectedDate(String dateRange,
			String technicianEmail) {
		return leaveDaoInt.countTechCancelledLeaveForSelectedDate(dateRange,technicianEmail);
	}

	@Override
	public int getApprovedLeaveCountByTechnician(String technicianEmail) {
		return leaveDaoInt.getActiveLeaveCountByTechnician(technicianEmail);
	}

	@Override
	public int getCancelledLeaveCountByTechnician(String technicianEmail) {
		return leaveDaoInt.getCancelledLeaveCountByTechnician(technicianEmail);
	}	

	@Override
	public List<Leave> getTechApprovedLeaveForSelectedRange(String dateRange,
			String technicianEmail) {
		return leaveDaoInt.getTechApprovedLeaveForSelectedRange(dateRange,technicianEmail);
	}

	@Override
	public List<Leave> getTechCancelledLeaveForSelectedRange(String dateRange,
			String technicianEmail) {		
		return leaveDaoInt.getTechCancelledLeaveForSelectedRange(dateRange,technicianEmail);
	}
	
	@Override
	public List<Leave> getAllPendingActiveApprovedAndCancelledLeave() {		
		return leaveDaoInt.getAllPendingActiveApprovedAndCancelledLeave();
	}

	@Override
	public List<Leave> pendingActiveApprovedAndCancelledLeaveByTechnician(
			String technicianEmail) {		
		return leaveDaoInt.pendingActiveApprovedAndCancelledLeaveByTechnician(technicianEmail);
	}
}
