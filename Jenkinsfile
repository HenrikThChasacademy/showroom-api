
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew assemble'
                echo 'Build'
            }
        }
        stage('Test'){
            steps {
                sh './gradlew test'
                echo 'Test'
            }
        }
        stage('Dockerize'){
            steps {
                sh 'docker build -t showroom:${env.BRANCH_NAME} .'
            }
        }
        stage('Push image to ECR'){
            steps {
                sh 'docker push 674186390846.dkr.ecr.eu-west-1.amazonaws.com/showroom:${env.BRANCH_NAME}'
            }
        }
    }
}