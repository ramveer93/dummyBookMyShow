pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean'
                
            }
        }
        stage('Test') {
            steps {
                sh 'mvn clean install'
                
            }
        }
    }
}
