pipeline {
    agent any
    
    tools{
        jdk 'jdk17'
        maven 'maven2'
    }
    environment{
        SCANNER_HOME= tool 'sonar-scanner'
    }
    

    stages {
        
        stage('Git Checkout ') {
            steps {
               git branch: 'main', url: 'https://github.com/Bhagavan1506/BoardgameListingWebApp.git'
            }
        }
        
        stage('Compile') {
            steps {
               sh 'mvn compile'
            }
        }
        
        stage('Test') {
            steps {
               sh 'mvn test'
            }
        }
        
        stage('File System Scan') {
            steps {
                sh 'trivy fs --format table -o trivy-fs-report.html .'
            }
        }
        
        stage('Sonar Analysis') {
            steps {
               withSonarQubeEnv('sonarQube'){
                   sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=OnlineGame \
                   -Dsonar.java.binaries=. \
                   -Dsonar.projectKey=OnlineGame '''
               }
            }
        }
        
     
        
        stage('Build') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Publish to Nexus') {
            steps {
                withMaven(globalMavenSettingsConfig: 'global-settings', jdk: 'jdk17', maven: 'maven2', mavenSettingsConfig: '', traceability: true) {
                     sh 'mvn deploy'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
               script{
                   withDockerRegistry(credentialsId: 'docker-creds',toolName: 'docker') {
                    sh "docker build -t bhagavanbongi/boardgame:latest ."
                 }
               }
            }
        }
        stage('Docker Image Scan') {
            steps {
                sh 'trivy image --format table -o trivy-fs-report1.html bhagavanbongi/boardgame:latest'
            }
        }
        
        stage('Docker Push') {
            steps {
               script{
                   withDockerRegistry(credentialsId: 'docker-creds',toolName: 'docker') {
                    sh "docker push bhagavanbongi/boardgame:latest"
                 }
               }
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f deployment-service.yaml'
            }
        }
        
        stage('Verify Deployment') {
            steps {
               sh 'kubectl get pods'
               sh 'kubectl get svc'
            }
        }
    }
}
