#/bin/bash

for i in `seq 1 8`
do
  useradd roboteam${i} 
  echo "Hackath0n${i}!" | passwd --stdin roboteam${i}
done
