pipeline {
    agent {label 'backendapi-java'}
    environment {
        AWS_ACCOUNT_ID="756688684263"
        AWS_DEFAULT_REGION="us-west-2" 
	CLUSTER_NAME="discovery-service-cluster-new"
	SERVICE_NAME="discovery-service-ecs-service-service"
	TASK_DEFINITION_NAME="first-run-task-definition"
	//DESIRED_COUNT="4"
	DESIRED_COUNT="2"
        IMAGE_REPO_NAME="discovery-service-ecr"
        IMAGE_TAG="${env.BUILD_ID}"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
	   //REPOSITORY_URI = "public.ecr.aws/f2x2f4b4/discovery-service-ecr"
	registryCredential = "awsadmin"
	// Override HOME to WORKSPACE
        HOME = "${WORKSPACE}"
        // or override default cache directory (~/.npm)
        //NPM_CONFIG_CACHE = "${WORKSPACE}/.npm"
		MAIL_TO ='kesavannarayanan@gmail.com,lobomalcon@gmail.com,sangramsahutech@gmail.com,anshulv1401@gmail.com,akshit.baunthy@gmail.com'
    }
   
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
        
    // Building Docker images
    stage('Building Docker image') {
      steps{
        script {
          dockerImage = docker.build "${IMAGE_REPO_NAME}:${IMAGE_TAG}"
        }
      }
    }
   
    // Uploading Docker images into AWS ECR
    stage('Pushing to AWS ECR') {
     steps{  
         script {
			docker.withRegistry("https://" + REPOSITORY_URI, "ecr:${AWS_DEFAULT_REGION}:" + registryCredential) {
                    	dockerImage.push()
                	}
         }
        }
      }
      
    stage('Deploy AWS ECS') {
     steps{
            withAWS(credentials: registryCredential, region: "${AWS_DEFAULT_REGION}") {
                script {
			sh 'chmod 777 script.sh'
			sh './script.sh'
                }
            } 
        }
      }      
      
    }
    post{
        always{
	    mail to:"${MAIL_TO}",
            subject: "Capstone Project-Bankend API-jenkins buil-AWS ECR->ECS:${currentBuild.currentResult}: ${env.JOB_NAME}",
            body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\n More Info can be found here: ${env.BUILD_URL}"
	   /*emailext to: "kesavannarayanan@gmail.com",
            subject: "Capston Project-Bankend API-jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
            body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\n More Info can be found here: ${env.BUILD_URL}"*/
        }
    }
}
