Steps to set up an OCP cluster for the Robot Hackthon

Adapt 
* hostnames/IPs
* SSH key names
* Ansible user name 

in inventory and group_vars/ocpnodes

Test with 
* ansible all -i inventory -m ping
* ansible ocpnodes -i inventory -m ping

VPN Setup:
* Generate a OVPN static key and place it into files/ as "static.key"
* Run the Ansible Playbook
* Change the OVPN server IP/Hostname in the client/VPN GW confguration
