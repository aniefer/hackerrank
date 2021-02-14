/*
 * https://www.hackerrank.com/challenges/matrix/problem
 */

use std::collections::HashMap;
use std::collections::HashSet;
use std::rc::Rc;
use std::cell::RefCell;


struct Node {
	node_id: i32,
	has_machines: bool,
	members: Vec<i32>,
}

impl Node {
	fn new(node_id: i32, has_machines: bool) -> Self {
		Self {
			node_id,
			has_machines,
			members: vec![node_id],
		}
	}
}

struct Graph {
	nodes : HashMap<i32, Rc<RefCell<Node>>>,
}

impl Graph {
	pub fn new() -> Self {
		Graph { nodes : HashMap::new()}
	}
	
	pub fn get_node(&mut self, node_id:i32, infected: bool) -> Rc<RefCell<Node>> {
		let node = self.nodes.entry(node_id)
			.or_insert_with(|| Rc::new(RefCell::new(Node::new(node_id, infected))));
		node.clone()
	}
	
	pub fn merge(&mut self, rc_node: &Rc<RefCell<Node>>, other: &Rc<RefCell<Node>>) {
		
		other.borrow_mut().members.iter().for_each(|idx| {
			self.nodes.insert(*idx, rc_node.clone());
		});

		let mut node = rc_node.borrow_mut();
		node.has_machines |= other.borrow().has_machines;
		node.members.append(&mut other.borrow_mut().members);
	}
}

#[allow(dead_code)]
pub fn min_time(roads: &mut Vec<[i32; 3]>, machines: Vec<i32>) -> i32 {
	let infected: HashSet<i32> = machines.into_iter().collect();
	roads.sort_unstable_by(|a, b| a[2].partial_cmp(&b[2]).unwrap().reverse());
	
	let mut time = 0;
	let mut graph = Graph::new();
	
	for road in roads {
		let node1: i32 = road[0];
		let node2: i32 = road[1];
		let weight: i32 = road[2];

		let g1 = graph.get_node(node1, infected.contains(&node1));
		let g2 = graph.get_node(node2, infected.contains(&node2));
		
		if g1.borrow().node_id != g2.borrow().node_id {
			// not the same group
			if g1.borrow().has_machines && g2.borrow().has_machines {
				// both have machines, this road is cut
				time += weight;
			} else {
				graph.merge(&g1, &g2);
			}
		}
	}
	time
}

#[cfg(test)]
mod tests {
	use super::*;
	#[test]
	fn test_case() {
		let mut roads = vec![[2, 1, 8], [1, 0, 5], [2, 4, 5], [1, 3, 4]];
		assert_eq!(10, min_time(&mut roads, vec![2, 4, 0]));

		roads = vec![[0, 1, 4], [1, 2, 3], [1, 3, 7], [0, 4, 2]];
		assert_eq!(5, min_time(&mut roads, vec![2, 3, 4]));
	}
}
