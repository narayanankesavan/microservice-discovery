pipeline {
  environment {
    registry = "gustavoapolinario/docker-test"
    registryCredential = 'dockerhub'
    dockerImage = ''
  }
  agent {label 'backendapi-java'}
  stages {
	stage('Maven Build') {
      steps{
        sh 'echo inside maven build'
        sh 'ls -al'
        dir("${env.WORKSPACE}"){
        sh 'echo inside dir'
        sh "pwd"
        sh 'mvn clean install'
        }
      }
    }
	stage('Remove Docker container') {
      steps{
		script {
			def contid = sh(script: "sudo docker ps --no-trunc -aqf name=microservice-discovery-service",returnStdout: true)
            echo "contid: " + "${contid}"
			if("${contid}" != ''){
			    sh "echo inside delete container"
				sh "sudo docker rm -f ${contid}"
			}
		 }
      }
    }
	stage('Remove Docker image') {
      steps{
	  dir("${env.WORKSPACE}"){
		script {
			def imageExists = sh(script: "sudo docker images -q microservice-discovery-service:latest",returnStdout: true)
            echo "Image Name: " + "${imageExists}"
			if("${imageExists}" != ''){
			    sh "echo inside delete block"
				sh "sudo docker rmi microservice-discovery-service:latest"
			}
		 }
		}
      }
    }
	stage('Building Docker image') {
      steps{
	  dir("${env.WORKSPACE}){
		sh "pwd"
		sh 'sudo docker build -f Dockerfile -t microservice-discovery-service .'
		}
      }
    }
	stage('Run Docker container') {
      steps{
	  script {
		sh "pwd"
		sh "sudo docker run -it -d --name microservice-discovery-service -p 8761:8761 microservice-discovery-service:latest"
		}
      }
    }
   }
 }
