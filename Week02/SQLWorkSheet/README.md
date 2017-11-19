# RMAN Oracle recovery manager
rman target sysbackup --login as sysbackup account

> show all; -- show all RMAN config settings including backup settings

after configuring path to disk ...

There will be noARCHIVEMODE backup erro 

run 

RMAN> run 
2> {
3> shutdown immediate
4> startup mount
5> backup database;
6> }

and creates .bkp file, cannot get .bak file 
