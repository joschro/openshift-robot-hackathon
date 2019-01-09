#!/usr/bin/env bash
export ANSIBLE_HOST_KEY_CHECKING=False

export OA_PREFIX=/usr/share/ansible

if [ $1 = "origin" ]; then
  OA_PREFIX=/home/{{amazon_user}}
fi

{% if ocp_version|version_compare('3.7', '<=')  %}
ansible-playbook -v -i ~{{amazon_user}}/openshift_inventory.cfg ${OA_PREFIX}/openshift-ansible/playbooks/byo/config.yml
{% else %}
ansible-playbook -v -i ~{{amazon_user}}/openshift_inventory.cfg ${OA_PREFIX}/openshift-ansible/playbooks/prerequisites.yml
ansible-playbook -v -i ~{{amazon_user}}/openshift_inventory.cfg ${OA_PREFIX}/openshift-ansible/playbooks/deploy_cluster.yml
{% endif %}
