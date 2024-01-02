package sb.park.bus.data

enum class BusType(val type: String, val typeName: String) {
    PORT("1", "공항"),
    COUNTRY("2", "마을"),
    GANSEON("3", "간선"),
    JISEON("4", "지선"),
    CYCLE("5", "순환"),
    WIDE("6", "광역"),
    INCHEON("7", "인천"),
    GYUNGGI("8", "경기"),
    COMMON("0", "공용")
}