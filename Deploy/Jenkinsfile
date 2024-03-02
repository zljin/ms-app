#!/usr/bin/groovy

node('api') {

    properties([
            parameters([
                    stringParam(name: "gitRepo", description: "app git repo", defaultValue: ""),
                    stringParam(name: "branch", description: "git branch in your app repo", defaultValue: ""),
                    stringParam(name: "environment", description: "release environment", defaultValue: "")
            ])
    ])

    stage("startUp"){
        env.artifactId = "ms-app"
        env.version = "0.0.1"
        env.nexusRepo = "192.168.56.1:8082"
        env.nexusUser = "admin"
        env.nexusPassword = "admin"
        env.uniqueVersion = env.version+":"+System.currentTimeMillis()
        env.imageNameTag = env.artifactId+"-"+ env.version + env.uniqueVersion
        //192.168.56.1:8082/ms-app-0.0.1:123332311
        env.imageNameTagRepo = env.nexusRepo+"/"+env.imageNameTag
        env.INGRESS_HOST = "ms-app.leonard.com"
    }

    stage("clean workspace") {
        cleanWs()
        sh("printenv")
    }


    stage("git clone") {
        checkout(scm)
    }

    stage("Sonar Scan"){
        //...
    }

    stage("maven build") {
        sh("mvn clean install -DskipTests")
        sh("chmod 777 ./target/*.jar")
    }

    stage("docker build") {
        sh("docker build -t ${imageNameTag}  .")
        sh("docker image")
    }

    stage("push image to nexus") {
        sh("docker login -u ${nexusUser} -p ${nexusPassword} ${nexusRepo}")
        sh("docker tag ${imageNameTag} ${imageNameTagRepo}")
        sh("docker images")
        sh("docker push ${imageNameTagRepo}")
    }

    stage("deploy to k8s") {
        def opsDir = env.WORKSPACE/yaml
        dir(opsDir){
            sh("cat deploy-template.yaml > deploy-${environment}.yaml ")
            sh("sed -i 's/APP_NAME/${env.artifactId}' deploy-${environment}.yaml ")
            sh("sed -i 's/REPO_URL/${env.nexusRepo}' deploy-${environment}.yaml ")
            sh("sed -i 's/IMAGE_TAG/${env.imageNameTag}' deploy-${environment}.yaml ")
            sh("sed -i 's/TARGET_PORT/8090' deploy-${environment}.yaml ")
            sh("sed -i 's/INGRESS_HOST/${env.INGRESS_HOST}' deploy-${environment}.yaml ")
            sh("kubectl -n app -f deploy-${environment}.yaml")
        }
    }
}