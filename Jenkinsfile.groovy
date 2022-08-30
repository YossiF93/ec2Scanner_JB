pipeline {
    agent any
    parameters {
        string defaultValue: '5', name: 'APP_BOOT_TIME_INTERVAL'
    }
    environment {
        CRED = credentials('credentials')
        CONFIG = credentials('config')
    }

    stages {
        stage('Initialize') {
            steps {
                cleanWs()
                sh "docker kill aws || true"
                sh "docker rm aws || true"
                sh "docker rmi -f aws || true"
            }
        }
        stage('Checkout SCM') {
            steps {
                git branch: 'main', url: 'https://github.com/YossiF93/ec2Scanner_JB.git'
            }
        }
        stage('Build') {
            steps {
                sh "cat $CRED | tee credentials"
                sh "cat $CONFIG | tee config"
                sh "docker build -t aws ."
            }
        }
        stage('Deploy') {
            steps {
                sh "docker run -itd --name aws --env APP_BOOT_TIME_INTERVAL=${params.APP_BOOT_TIME_INTERVAL} aws"
            }
        }
    }
}