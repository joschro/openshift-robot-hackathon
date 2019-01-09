---
ansible_ssh_private_key_file: "~/.ssh/KEYNAME"
# Change bastion IP here
ansible_ssh_common_args: '-o ProxyCommand="ssh -W %h:%p -i ~/.ssh/KEYNAME -q ec2-user@HOSTNAME"' 

