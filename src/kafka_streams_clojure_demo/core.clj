(ns kafka-streams-clojure-demo.core
  (:require [clojure.string :as str])
  (:import [org.apache.kafka.streams StreamsBuilder StreamsConfig KafkaStreams Topology]
           [org.apache.kafka.streams.kstream KStream ValueMapper]
           [org.apache.kafka.common.serialization Serdes]))

(defn- value-mapper [process]
  (reify
    ValueMapper
    (apply [_ v] (process v))))

(defn- to-uppercase [s] 
  (clojure.string/upper-case s))

(defn- case-conversion-topology [input-topic output-topic] 
  (let [builder (StreamsBuilder.)]
    (->
      (.stream builder input-topic)
      (.mapValues (value-mapper to-uppercase))
      (.to output-topic))
    (.build builder)))

(def properties
  {StreamsConfig/APPLICATION_ID_CONFIG                    "demo-application"
   StreamsConfig/BOOTSTRAP_SERVERS_CONFIG                 "localhost:9092"
   StreamsConfig/DEFAULT_KEY_SERDE_CLASS_CONFIG           (.getName (.getClass (Serdes/String)))
   StreamsConfig/DEFAULT_VALUE_SERDE_CLASS_CONFIG         (.getName (.getClass (Serdes/String)))})

(defn -main [& args] 
  (let [topology (case-conversion-topology "lowercase" "uppercase")
        stream (KafkaStreams. topology (StreamsConfig. properties))]
    (.addShutdownHook (Runtime/getRuntime) (Thread. (fn [] (do (.close stream)))))
    (.start stream)))