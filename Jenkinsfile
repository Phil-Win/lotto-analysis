import java.text.SimpleDateFormat

node("docker_slave") {
    def customImage
    def latestImage
    def date    =   new Date()

    stage("Setting up") {
        deleteDir()
        checkout scm
        sh "./gradlew build"
    }
    stage("Create Docker image") {
        def dateFormat =   new SimpleDateFormat("yyyy_MM_dd")
        customImage = docker.build "philwin/lotto-analysis" + ":${env.BRANCH_NAME}_${BUILD_NUMBER}_date_${dateFormat.format(date)}"
        latestImage = docker.build "philwin/lotto-analysis" + ":latest"
    }
    stage ("Publish Docker Image") {
        customImage.push()
        latestImage.push()
    }
}