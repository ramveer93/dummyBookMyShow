pipeline {
    agent any
    tools {
        maven 'maven-3.6.3'
        jdk 'jdk-9.0.4'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
        stage ('Test') {
            steps {
                sh 'mvn clean install' 
            }
        }
    }
}
