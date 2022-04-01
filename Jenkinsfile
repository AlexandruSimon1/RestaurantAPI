pipeline {
    agent any
    triggers {
            githubPush()
    }
    stages {
        stage("Read from Maven POM"){
            steps{
                script{
                    projectArtifactId = readMavenPom().getArtifactId()
                    projectVersion = readMavenPom().getModelVersion()
                }
                echo "Building ${projectArtifactId}:${projectVersion}"
            }
        }
//         stage("Test"){
//             steps {
//                 bat "mvn -version"
//                 bat "mvn test"
//             }
//         }
        stage("Build JAR file"){
            steps{
                sh script: "mvn install -Dmaven.test.skip=true"
            }
        }
        stage("Build Docker image"){
                    steps {
                        echo "Building service image and pushing it to DockerHub"
                            withCredentials([usernamePassword(credentialsId: 'Docker', usernameVariable: "dockerLogin",
                                passwordVariable: "dockerPassword"),
                                string(credentialsId: 'DecryptPassword',variable: "password"),
                                string(credentialsId: 'Database-RDS-URL',variable: "database")
                        ]) {
                                    sh script: "docker login -u ${dockerLogin} -p ${dockerPassword}"
                                    sh script: "docker image build --build-arg PASSWORD=${password} --build-arg DATABASE=${database} -t ${dockerLogin}/restaurant ."
                                    sh script: "docker push ${dockerLogin}/restaurant"
                        }
                        echo "Building image and pushing it to DockerHub is successful done"
                    }
                }
                stage("Deploy On AWS EC2 Instance"){
                            steps{
                                withCredentials([string(credentialsId: 'DecryptPassword',variable: "password"),
                                                string(credentialsId: 'Restaurant-EC2-URL',variable: "host"),
                                                string(credentialsId: 'Database-RDS-URL',variable: "database"),
                                                usernamePassword(credentialsId: 'Docker', usernameVariable: "dockerLogin",
                                                    passwordVariable: "dockerPassword"),
                                                sshUserPrivateKey(credentialsId: 'AWS-Keypair', keyFileVariable: 'identity', passphraseVariable: '',
                                                usernameVariable: 'userName')
                        ]){
                                 script{
                                def remote = [:]
                                    remote.user = userName
                                    remote.host = host
                                    remote.name = userName
                                    remote.identityFile = identity
                                    remote.allowAnyHosts = 'true'
                                    sshCommand remote: remote, command: 'docker container kill $(docker ps -a -q)'
                                    sshCommand remote: remote, command: 'docker rm $(docker ps -a -q)'
                                    sshCommand remote: remote, command: 'docker rmi $(docker images -q)'
                                    sshCommand remote: remote, command: "docker login | docker pull ${dockerLogin}/restaurant"
                                    sshCommand remote: remote, command: "docker container run --env PASSWORD=${password} --env DATABASE=${database} -d -p 80:8282 --name restaurant ${dockerLogin}/restaurant"
                                    sshCommand remote: remote, command: "exit"
                            }
                                timeout(time: 90, unit: 'SECONDS') {
                                waitUntil(initialRecurrencePeriod: 2000) {
                                    script {
                                        def result =
                                        sh script: "curl -k --silent --output /dev/null https://${host}:80/restaurant-system-api/api/v1/menus",
                                        returnStatus: true
                                        return (result == 0)
                                    }
                                }
                            }
                                echo "Server is up"
                        }
                    }
                }
//         stage("Deploy Locally"){
//             steps{
//                 sh script: "docker-compose --file docker-compose.yml up --detach"
//                 timeout(time: 60, unit: 'SECONDS') {
//                 waitUntil(initialRecurrencePeriod: 2000) {
//                     script {
//                         def result =
//                         sh script: "curl --silent --output /dev/null http://localhost:8282/waiters",
//                         returnStatus: true
//                         return (result == 0)
//                         }
//                     }
//                 }
//                 echo "Server is up"
//             }
//         }
//         stage("Newman Test"){
//             steps{
//                 echo "Starting Newman Test"
//                 bat "newman run --disable-unicode https://www.getpostman.com/collections/345d1665e5bdd9ca448e"
//             }
//         }
//         stage("JMeter Loading Test"){
//             steps{
//             echo "Starting the JMeter Loading Test"
//             bat "jmeter -jjmeter.save.saveservice.output_format.xml -n -t D:/RestaurantAPI.jmx -l D:/report.jtl"
//             }
//         }
                stage("Clean Docker Images"){
                                steps{
                                echo "Starting Deleting Created Docker Images"
                                sh script: 'docker rmi $(docker images -q)'
                }
            }
    }
    post {
        always {
            cleanWs()
        }
    }
}
