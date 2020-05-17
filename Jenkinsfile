def mvnHome

node('node'){
   stage('git checkout'){
      try {
      git credentialsId: 'git-token', url: 'https://github.com/neorusse/estore'
      } catch(err) {
         sh "echo error in checkout"
      }
   }

   dir('spring-jenkins-pipeline') {
      stage("Compilation and Analysis") {
         parallel 'Compilation': {
            mvnHome=tool 'maven-3.6.3'
            sh "${mvnHome}/bin/mvn --version"
            sh "${mvnHome}/bin/mvn clean install -DskipTests"
         },
         'Static Analysis': {
           stage("Checkstyle") {
              sh "${mvnHome}/bin/mvn checkstyle:checkstyle"
               step([$class: 'CheckStylePublisher',
                 canRunOnFailed: true,
                 defaultEncoding: '',
                 healthy: '100',
                 pattern: '**/target/checkstyle-result.xml',
                 unHealthy: '90',
                 useStableBuildAsReference: true
               ])
         } }
      }
   }

   stage('maven test'){
     try {
        sh "${mvnHome}/bin/mvn clean test surefire-report:report"
     } catch(err) {
        sh "echo error in defining maven"
     }
   }

   stage('test case and report'){
     try {
        echo "executing test cases"
        junit allowEmptyResults: true, testResults: 'addressbook_main/target/surefire-reports/*.xml'
        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'addressbook_main/target/site', reportFiles: 'surefire-report.html', reportName: 'SureFireReportHTML', reportTitles: ''])
     } catch(err) {
        throw err
     }
   }

   stage ('docker build and push'){
         try {
          sh "docker version"
          sh "docker build -t ecoden/estoreapp -f Dockerfile ."
          sh "docker run -p 8080:8080 -d ecoden/estoreapp"
          withDockerRegistry(credentialsId: 'docker-hub-registry') {
          sh "docker push ecoden/estoreapp"
           }
         } catch(err) {
            sh "echo error in docker build and pushing to docker hub"
         }
      }

}