#!/usr/bin/env bash

# Write a "secrets" file like this:
## AWS IAM key goes here
#export AWS_ACCESS_KEY_ID=""
#export AWS_SECRET_ACCESS_KEY=""
#
## The private SSH key to access the instances 
#export SSHPRIVKEY="~/.ssh/keyname"
#
## For OpenShift Enterprise deployment, RHSM registration info goes here
#export ACTKEY="activationkey name"
#export ORGID="orgid"
#
## Token for Service Account to access RH Container Registry
## https://access.redhat.com/terms-based-registry
#export TOKEN_USER=""
#export TOKEN=""

# Source it to make env vars available: ". secrets"

: ${AWS_ACCESS_KEY_ID?"Need to set AWS_ACCESS_KEY_ID"}
: ${AWS_SECRET_ACCESS_KEY?"Need to set AWS_SECRET_ACCESS_KEY"}
: ${ACTKEY?"Need to set ACTKEY"}
: ${ORGID?"Need to set ORGID"}
: ${TOKEN_USER?"Need to set TOKEN_USER"}
: ${TOKEN?"Need to set TOKEN"}
: ${SSHPRIVKEY?"Need to set SSHPRIVKEY"}

export ANSIBLE_HOST_KEY_CHECKING=False

time ansible-playbook openshift-playbook.yml -i inventory/inventory.cfg \
  -e rhsm_key_id="$ACTKEY" \
  -e rhsm_org_id="$ORGID" \
  -e oreg_auth_user="$TOKEN_USER" \
  -e oreg_auth_password="$TOKEN" \
  -e ansible_ssh_private_key_file="$SSHPRIVKEY"

# use this if you want to pass all extras vars
#time ansible-playbook openshift-playbook.yml -i inventory/inventory.cfg "$@"
