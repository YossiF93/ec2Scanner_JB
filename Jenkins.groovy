
pipeline{
	agent any
	parameters{
        string defaultValue: '3000', name: 'AppBootTimeInterval'
    } 
	stages{
		stage('init'){
			steps{
			cleanWs()

			}
        }
        stage('Get SCM'){
			steps{

        }
        stage('Docker build'){
			steps{
            
            if(docker.ps = som ){
                kill
            }
            sh "docker build -t ec2Scanner:${currentBuild.number}" 
            sh "docker run -t ec2Scanner:${currentBuild.number}" 
        }        
        stage('Deploy'){
			steps{
			cleanWs()
			}
        }        
                
			