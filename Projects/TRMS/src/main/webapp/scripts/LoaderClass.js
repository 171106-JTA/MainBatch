class Loader{
	constructor(data){
		var reimbursementevents = data.reimbursementevents;
		var positions = data.positions;
		var facilities = data.facilities;
		var approvalstatuses = data.approvalstatuses;
		function getReimbursementEvents(){
			return this.reimbursementevents;
		}
		function getPositions(){
			return this.positions;
		}
		function getFacilities(){
			return this.facilities;
		}
		function getApprovalStatuses(){
			return this.approvalstatuses;
		}
	}
}