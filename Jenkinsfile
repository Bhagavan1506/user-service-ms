pipeline {
    agent any

    tools {
        maven "MAVEN"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/javaexpresschannel/user-service.git'
            }
        }

        stage('Compile') {
            steps {
                sh "mvn clean compile"
            }
        }

        stage('Build') {
            steps {
                sh "mvn package"
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t javaexpress/user-service:latest .'
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([string(credentialsId: 'DOCKER_HUB_PASSWORD', variable: 'PASSWORD')]) {
                    sh 'docker login -u javaexpress -p $PASSWORD'
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh 'docker push javaexpress/user-service:latest'
            }
        }

        stage("Kubernetes Deployment") {
            steps {
                sh '''
                    echo "Deleting old deployment (if exists)..."
                    kubectl delete deployment user-service-deployment --ignore-not-found

                    echo "Waiting for cleanup..."
                    sleep 5

                    echo "Applying fresh deployment..."
                    kubectl apply -f user_deployment.yaml

                    echo "Waiting for deployment to stabilize..."
                    sleep 10

                    echo "Checking rollout status..."
                    kubectl rollout status deployment user-service-deployment
                '''
            }
        }
    }
}
