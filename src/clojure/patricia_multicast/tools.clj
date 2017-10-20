(ns patricia-multicast.tools
  (:import [java.net InetAddress DatagramPacket MulticastSocket]))

(defn localhost [] (.getLocalHost InetAddress))

(defn inet-address [s] (InetAddress/getByName s))

(defn datagram-packet [bytes length]
  (DatagramPacket. bytes length))

(defn message [text group port]
  (DatagramPacket. (.getBytes text)
                   (.length text)
                   group
                   port))

(defn empty-message [n]
  (DatagramPacket. (byte-array n) n))

(defn bytes-message [abytes address port]
  (DatagramPacket. abytes (alength abytes) address port))

(defn multicast-socket [port] (MulticastSocket. port))

(defn dp-receive-msg [ms bytes-length]
  (let [ab (byte-array bytes-length)
        dp (datagram-packet ab (alength ab))]
    (.receive ms dp)
    (-> dp .getData String. .trim)))

(defn send-message [ms text group port]
  (->> (message text group port)
       (.send ms)))

