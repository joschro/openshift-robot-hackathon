for i in `seq -w 0000 0060`
do SHARE=/nfs_export/pv$i
 mkdir -p $SHARE
 chmod 777 $SHARE
 chown nfsnobody:nfsnobody $SHARE
 echo "$SHARE 192.168.0.0/24(rw,root_squash,no_wdelay)" >> /etc/exports
done
