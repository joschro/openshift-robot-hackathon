bastion ansible_host=HOSTNAME ansible_ssh_private_key_file="~/.ssh/KEYNAME" ansible_user=ec2-user

# nodes are expected to be reachable through bastion via SSH proxy
# adapt group_vars/ocpnodes
[ocpnodes]
master ansible_host=INTIPMASTER ansible_user=ec2-user
node01 ansible_host=INTIPNODE1 ansible_user=ec2-user
node02 ansible_host=INTIPNODE2 ansible_user=ec2-user
node03 ansible_host=INTIPNODE3 ansible_user=ec2-user


