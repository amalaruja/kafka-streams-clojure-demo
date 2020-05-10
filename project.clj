(defproject kafka-streams-clojure-demo "0.1.0-SNAPSHOT"
  :description "This is a demo Kafka Stream application"
  :url "https://github.com/amalaruja/kafka-streams-clojure-demo"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.apache.kafka/kafka-streams "2.4.1"]]
  :main ^:skip-aot kafka-streams-clojure-demo.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
