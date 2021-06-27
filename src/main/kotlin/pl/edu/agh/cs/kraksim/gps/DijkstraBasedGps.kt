package pl.edu.agh.cs.kraksim.gps

import pl.edu.agh.cs.kraksim.common.addToFront
import pl.edu.agh.cs.kraksim.common.adjacentPairs
import pl.edu.agh.cs.kraksim.common.popMinBy
import pl.edu.agh.cs.kraksim.core.state.Gateway
import pl.edu.agh.cs.kraksim.core.state.Road
import pl.edu.agh.cs.kraksim.core.state.RoadNode
import pl.edu.agh.cs.kraksim.core.state.SimulationState

abstract class DijkstraBasedGps(
    source: Gateway,
    target: Gateway,
    state: SimulationState
) : GPS {

    override val route = ArrayList<Road>()

    abstract fun getRoadWeight(node: Road): Double

    init {
        if (source != target) {
            calculateDijkstra(state, source, target)
        }
    }

    private fun calculateDijkstra(state: SimulationState, source: Gateway, target: Gateway) {
        val (notReachedNodes, weightFromSource, pathRecovery) = initialize(state, source)

        calculateShortestPath(notReachedNodes, weightFromSource, pathRecovery, target)
        parseRecoveryToRoute(target, pathRecovery)
    }

    private fun initialize(
        state: SimulationState,
        source: Gateway
    ): Triple<HashSet<RoadNode>, HashMap<RoadNode, Double>, HashMap<RoadNode, RoadNode>> {
        val notReachedNodes = HashSet<RoadNode>()
        val weightFromSource = HashMap<RoadNode, Double>()
        val pathRecovery = HashMap<RoadNode, RoadNode>()

        for (node in state.gateways + state.intersections) {
            weightFromSource[node] = Double.MAX_VALUE
            notReachedNodes.add(node)
        }
        weightFromSource[source] = 0.0

        return Triple(notReachedNodes, weightFromSource, pathRecovery)
    }

    private fun calculateShortestPath(
        notReachedNodes: HashSet<RoadNode>,
        weightFromSource: HashMap<RoadNode, Double>,
        pathRecovery: HashMap<RoadNode, RoadNode>,
        target: Gateway
    ) {
        while (notReachedNodes.isNotEmpty()) {
            val currentNode = notReachedNodes.popMinBy { weightFromSource[it]!! }

            if (currentNode == target) {
                return
            }

            for (roadFromCurrentNode in currentNode.startingRoads) {
                val neighbourNode = roadFromCurrentNode.end()
                val neighbourNodeWeightFromSource =
                    weightFromSource[currentNode]!! + getRoadWeight(roadFromCurrentNode)

                if (neighbourNodeWeightFromSource < weightFromSource[neighbourNode]!!) {
                    weightFromSource[neighbourNode] = neighbourNodeWeightFromSource
                    pathRecovery[neighbourNode] = currentNode
                }
            }
        }
    }

    private fun parseRecoveryToRoute(target: Gateway, pathRecovery: HashMap<RoadNode, RoadNode>) {
        val nodeRoute = arrayListOf<RoadNode>(target)

        var node: RoadNode? = pathRecovery[target]
        while (node != null) {
            nodeRoute.addToFront(node)
            node = pathRecovery[node]
        }
        nodeRoute.adjacentPairs().forEach { (node, nextNode) ->
            val el: Road = node.startingRoads.find { it.end() == nextNode }!!
            route.add(el)
        }
    }

    override fun getNext(): Road =
        route.first()

    override fun popNext(): Road =
        route.removeAt(0)
}
