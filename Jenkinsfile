pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'clean'
                
            }
        }
        stage('Test') {
            steps {
                sh 'clean install'
                
            }
        }
    }
}
