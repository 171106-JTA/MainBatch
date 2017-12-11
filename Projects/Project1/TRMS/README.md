#TRMS
Goal: create a website that streamlines review and approval of employee reimbursements 
## Requirements:
> Biz Logic
* $1000 per employee, per year; reset on the new year
* diff coverage for each event-type:
  - other                     (30%)
  - seminar                   (60%)
  - certification prep class  (75%)
  - university course         (80%)
  - technical training        (90%)
  - certification             (100%)
* after BENCO application receipt/approval, reimbursement is pending and awarded dependent upon passing-grade/presentation
  * approval req? put in on time
* monetary amt available defined by: available = total - pending - awarded  (a = 1000 - p - w)
  * if projected reimbursement amt exceeds availabe, projected is adjusted to amt available (in its entirety)

> Reimbursement Form
- must be completed at least **one week** prior (generate timestamp datafield upon insertion)
- form includes: basic emp info, basic event info (date time location description cost gradeformat type reason)
  - after cost and type is input, display projected reimbursement at bottom of page
- optional(for them, not me): event-attchment, approval-email/type, work hrs missed
- goto confirmation page, then redirect to home; or go back home w confirmation banner displayed up top

> Approval
- direct supervisor first filtering layer
- if don't approve 
  