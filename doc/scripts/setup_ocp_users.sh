#/bin/bash

for i in `seq 1 8`
do
 oc project default
 oc create user roboteam${i}
 htpasswd -b /etc/origin/master/htpasswd roboteam${i} Hackath0n${i}!
 oc new-project roboteam${i}-project
 oc project roboteam${i}-project
 oc policy add-role-to-user admin roboteam${i}
done

