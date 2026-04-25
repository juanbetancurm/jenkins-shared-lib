def call(Map config) {
    pipeline {
        agent any

        stages {
            stage('Pull from Docker Hub') {
                steps {
                    sh "docker pull ${config.dockerRepo}:${config.imageName}-${config.imageTag}"
                }
            }

            stage('Deploy') {
                steps {
                    sh """
                        docker rm -f ${config.imageName} || true
                        docker run -d --name ${config.imageName} -p ${config.appPort}:3000 ${config.dockerRepo}:${config.imageName}-${config.imageTag}
                    """
                }
            }
        }
    }
}
