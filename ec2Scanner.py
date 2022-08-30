import os
import boto3
import schedule

AWS_REGION = "eu-central-1"
EC2_RESOURCE = boto3.resource('ec2', region_name=AWS_REGION)
INSTANCE_NAME_TAG_KEY = 'K8s.io/role/master'
INSTANCE_NAME_TAG_VALUE = 1
INSTANCE_STATE_CODE = 16
APP_BOOT_TIME_INTERVAL = int(os.getenv('APP_BOOT_TIME_INTERVAL'))


def get_active_ec2_instances():
    global instances
    instances = EC2_RESOURCE.instances.filter(
        Filters=[
            {
                'Name': INSTANCE_NAME_TAG_KEY,
                'Values': [
                    INSTANCE_NAME_TAG_VALUE
                ]
            },
            {
                'Name': 'instance-state-code',
                'Values': [
                    INSTANCE_STATE_CODE
                ]
            }
        ]
    )
    print(f' Running EC2 Instances:')
    for instance in instances:
        print(f' EC2 Instance Name: {instance.name},EC2 Instance ID: {instance.id} ')


if APP_BOOT_TIME_INTERVAL is None:
    get_active_ec2_instances()
else:
    while True:
        schedule.run_pending()
        schedule.every(APP_BOOT_TIME_INTERVAL).minutes.do(get_active_ec2_instances())
