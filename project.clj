(require 'cemerick.pomegranate.aether)
(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))
 
(defproject tahto/foundation-web "4.0.1"
  :description "web libraries for foundation"
  :url "https://www.github.com/zcaudate/foundation-web"
  
  :aliases
  {"publish"     ["exec" "-ep" "(use 'code.doc)     (deploy-template :all) (publish :all)"]
   "incomplete"  ["exec" "-ep" "(use 'code.manage)  (incomplete :all) (System/exit 0)"]
   "install"     ["exec" "-ep" "(use 'code.maven)  (install :all {:tag :all}) (System/exit 0)"]
   
   "push-web-code"  ["run" "-m" "component.task-native-index"]}
  
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [foundation/code.test           "4.0.1"]
                 [foundation/code.manage         "4.0.1"]
                 [foundation/code.java           "4.0.1"]
                 [foundation/code.maven          "4.0.1"]
                 [foundation/code.doc            "4.0.1"]
                 [foundation/code.dev            "4.0.1"]
                 
                 [foundation/js.core             "4.0.1"]
                 [foundation/js.cell             "4.0.1"]
                 [foundation/js.lib.datetime     "4.0.1"]
                 [foundation/js.lib.ethereum     "4.0.1"]
                 [foundation/js.lib.rn           "4.0.1"]
                 [foundation/js.lib.lw-charts    "4.0.1"]
                 [foundation/js.lib.valtio       "4.0.1"]
                 [foundation/js.react            "4.0.1"]
                 [foundation/js.react-ext        "4.0.1"]
                 [foundation/js.react-native     "4.0.1"]
                 
                 [foundation/jvm                 "4.0.1"]
                 [foundation/net.http            "4.0.1"]

                 [foundation/rt.basic            "4.0.1"]
                 [foundation/rt.browser          "4.0.1"]
                 
                 [foundation/script.css          "4.0.1"]
                 [foundation/script.sql          "4.0.1"]
                 [foundation/std.lib             "4.0.1"]
                 [foundation/std.log             "4.0.1"]
                 [foundation/std.lang            "4.0.1"]
                 [foundation/std.text            "4.0.1"]
                 [foundation/xtalk.lang          "4.0.1"]]
  :profiles {:dev {:plugins [[lein-ancient "0.6.15"]
                             [lein-exec "0.3.7"]
                             [lein-cljfmt "0.7.0"]
                             [cider/cider-nrepl "0.25.11"]]}
             :repl {:injections [(do 
                                   (require '[std.lib :as h])
                                   (require '[std.lib.link :as link])
                                   (create-ns '.))
                                 (require 'jvm.tool)]}}
  :resource-paths    ["resources" "src-build" "test-data"]
  :repl-options {:host "0.0.0.0" :port 10234}
  :jvm-opts
  ["-Xms1536m"
   "-Xmx1536m"
   "-XX:MaxMetaspaceSize=1536m"
   "-XX:-OmitStackTraceInFastThrow"

   ;;
   ;; GC FLAGS
   ;;
   "-XX:+UseAdaptiveSizePolicy"
   "-XX:+AggressiveHeap"
   "-XX:+ExplicitGCInvokesConcurrent"
   "-XX:+UseCMSInitiatingOccupancyOnly"
   "-XX:+CMSClassUnloadingEnabled"
   "-XX:+CMSParallelRemarkEnabled"

   ;;
   ;; GC TUNING
   ;;   
   "-XX:MaxNewSize=256m"
   "-XX:NewSize=256m"
   "-XX:CMSInitiatingOccupancyFraction=60"
   "-XX:MaxTenuringThreshold=8"
   "-XX:SurvivorRatio=4"
   
   ;;
   ;; JVM
   ;;
   "-Djdk.tls.client.protocols=\"TLSv1,TLSv1.1,TLSv1.2\""
   "-Djdk.attach.allowAttachSelf=true"
   "--add-opens" "javafx.graphics/com.sun.javafx.util=ALL-UNNAMED"
   "--add-opens" "java.base/java.lang=ALL-UNNAMED"
   "--illegal-access=permit"


   ;;
   ;; RMI
   ;;
   "-Djava.rmi.server.hostname=192.168.0.10"
   "-Dcom.sun.management.jmxremote"
   "-Dcom.sun.management.jmxremote.port=9011"
   "-Dcom.sun.management.jmxremote.rmi.port=9011"
   "-Dcom.sun.management.jmxremote.local.only=false"
   "-Dcom.sun.management.jmxremote.authenticate=false"
   "-Dcom.sun.management.jmxremote.ssl=false"
   ])
