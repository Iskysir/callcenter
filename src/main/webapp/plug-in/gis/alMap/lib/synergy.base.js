/*
---
MooTools: the javascript framework

web build:
 - http://mootools.net/core/9a096f8b73c58eef1ab7c6def2dca9bb

packager build:
 - packager build Core/Object Core/Class.Extras

...
 */

/*
 ---

 name: Core

 description: The heart of MooTools.

 license: MIT-style license.

 copyright: Copyright (c) 2006-2012 [Valerio Proietti](http://mad4milk.net/).

 authors: The MooTools production team (http://mootools.net/developers/)

 inspiration:
 - Class implementation inspired by [Base.js](http://dean.edwards.name/weblog/2006/03/base/) Copyright (c) 2006 Dean Edwards, [GNU Lesser General Public License](http://opensource.org/licenses/lgpl-license.php)
 - Some functionality inspired by [Prototype.js](http://prototypejs.org) Copyright (c) 2005-2007 Sam Stephenson, [MIT License](http://opensource.org/licenses/mit-license.php)

 provides: [Core, MooTools, Type, typeOf, instanceOf, Native]

 ...
 */

(function() {

	this.MooTools = {
		version : '1.4.5',
		build : 'ab8ea8824dc3b24b6666867a2c4ed58ebb762cf0'
	};
	// typeOf, instanceOf

	var typeOf = this.typeOf = function(item) {
		if (item == null)
			return 'null';
		if (item.$family != null)
			return item.$family();

		if (item.nodeName) {
			if (item.nodeType == 1)
				return 'element';
			if (item.nodeType == 3)
				return (/\S/).test(item.nodeValue) ? 'textnode' : 'whitespace';
		} else if (typeof item.length == 'number') {
			if (item.callee)
				return 'arguments';
			if ('item' in item)
				return 'collection';
		}

		return typeof item;
	};

	var instanceOf = this.instanceOf = function(item, object) {
		if (item == null)
			return false;
		var constructor = item.$constructor || item.constructor;
		while (constructor) {
			if (constructor === object)
				return true;
			constructor = constructor.parent;
		}
		/* <ltIE8> */
		if (!item.hasOwnProperty)
			return false;
		/* </ltIE8> */
		return item instanceof object;
	};

	// Function overloading

	var Function = this.Function;

	var enumerables = true;
	for ( var i in {
		toString : 1
	})
		enumerables = null;
	if (enumerables)
		enumerables = [ 'hasOwnProperty', 'valueOf', 'isPrototypeOf',
				'propertyIsEnumerable', 'toLocaleString', 'toString',
				'constructor' ];

	Function.prototype.overloadSetter = function(usePlural) {
		var self = this;
		return function(a, b) {
			if (a == null)
				return this;
			if (usePlural || typeof a != 'string') {
				for ( var k in a)
					self.call(this, k, a[k]);
				if (enumerables)
					for (var i = enumerables.length; i--;) {
						k = enumerables[i];
						if (a.hasOwnProperty(k))
							self.call(this, k, a[k]);
					}
			} else {
				self.call(this, a, b);
			}
			return this;
		};
	};

	Function.prototype.overloadGetter = function(usePlural) {
		var self = this;
		return function(a) {
			var args, result;
			if (typeof a != 'string')
				args = a;
			else if (arguments.length > 1)
				args = arguments;
			else if (usePlural)
				args = [ a ];
			if (args) {
				result = {};
				for (var i = 0; i < args.length; i++)
					result[args[i]] = self.call(this, args[i]);
			} else {
				result = self.call(this, a);
			}
			return result;
		};
	};

	Function.prototype.extend = function(key, value) {
		this[key] = value;
	}.overloadSetter();

	Function.prototype.implement = function(key, value) {
		this.prototype[key] = value;
	}.overloadSetter();

	// From

	var slice = Array.prototype.slice;

	Function.from = function(item) {
		return (typeOf(item) == 'function') ? item : function() {
			return item;
		};
	};

	Array.from = function(item) {
		if (item == null)
			return [];
		return (Type.isEnumerable(item) && typeof item != 'string') ? (typeOf(item) == 'array') ? item
				: slice.call(item)
				: [ item ];
	};

	Number.from = function(item) {
		var number = parseFloat(item);
		return isFinite(number) ? number : null;
	};

	String.from = function(item) {
		return item + '';
	};

	// hide, protect

	Function.implement({

		hide : function() {
			this.$hidden = true;
			return this;
		},

		protect : function() {
			this.$protected = true;
			return this;
		}

	});

	// Type

	var Type = this.Type = function(name, object) {
		if (name) {
			var lower = name.toLowerCase();
			var typeCheck = function(item) {
				return (typeOf(item) == lower);
			};

			Type['is' + name] = typeCheck;
			if (object != null) {
				object.prototype.$family = (function() {
					return lower;
				}).hide();

			}
		}

		if (object == null)
			return null;

		object.extend(this);
		object.$constructor = Type;
		object.prototype.$constructor = object;

		return object;
	};

	var toString = Object.prototype.toString;

	Type.isEnumerable = function(item) {
		return (item != null && typeof item.length == 'number' && toString
				.call(item) != '[object Function]');
	};

	var hooks = {};

	var hooksOf = function(object) {
		var type = typeOf(object.prototype);
		return hooks[type] || (hooks[type] = []);
	};

	var implement = function(name, method) {
		if (method && method.$hidden)
			return;

		var hooks = hooksOf(this);

		for (var i = 0; i < hooks.length; i++) {
			var hook = hooks[i];
			if (typeOf(hook) == 'type')
				implement.call(hook, name, method);
			else
				hook.call(this, name, method);
		}

		var previous = this.prototype[name];
		if (previous == null || !previous.$protected)
			this.prototype[name] = method;

		if (this[name] == null && typeOf(method) == 'function')
			extend.call(this, name, function(item) {
				return method.apply(item, slice.call(arguments, 1));
			});
	};

	var extend = function(name, method) {
		if (method && method.$hidden)
			return;
		var previous = this[name];
		if (previous == null || !previous.$protected)
			this[name] = method;
	};

	Type.implement({

		implement : implement.overloadSetter(),

		extend : extend.overloadSetter(),

		alias : function(name, existing) {
			implement.call(this, name, this.prototype[existing]);
		}.overloadSetter(),

		mirror : function(hook) {
			hooksOf(this).push(hook);
			return this;
		}

	});

	new Type('Type', Type);

	// Default Types

	var force = function(name, object, methods) {
		var isType = (object != Object), prototype = object.prototype;
		if (isType)
			object = new Type(name, object);

		for (var i = 0, l = methods.length; i < l; i++) {
			var key = methods[i], generic = object[key], proto = prototype[key];

			if (generic)
				generic.protect();
			if (isType && proto)
				object.implement(key, proto.protect());
		}

		if (isType) {
			var methodsEnumerable = prototype.propertyIsEnumerable(methods[0]);
			object.forEachMethod = function(fn) {
				if (!methodsEnumerable)
					for (var i = 0, l = methods.length; i < l; i++) {
						fn.call(prototype, prototype[methods[i]], methods[i]);
					}
				for ( var key in prototype)
					fn.call(prototype, prototype[key], key)
			};
		}

		return force;
	};

	force(
			'String',
			String,
			[ 'charAt', 'charCodeAt', 'concat', 'indexOf', 'lastIndexOf',
					'match', 'quote', 'replace', 'search', 'slice', 'split',
					'substr', 'substring', 'trim', 'toLowerCase', 'toUpperCase' ])
			(
					'Array',
					Array,
					[ 'pop', 'push', 'reverse', 'shift', 'sort', 'splice',
							'unshift', 'concat', 'join', 'slice', 'indexOf',
							'lastIndexOf', 'filter', 'forEach', 'every', 'map',
							'some', 'reduce', 'reduceRight' ])(
					'Number',
					Number,
					[ 'toExponential', 'toFixed', 'toLocaleString',
							'toPrecision' ])('Function', Function,
					[ 'apply', 'call', 'bind' ])('RegExp', RegExp,
					[ 'exec', 'test' ])(
					'Object',
					Object,
					[ 'create', 'defineProperty', 'defineProperties', 'keys',
							'getPrototypeOf', 'getOwnPropertyDescriptor',
							'getOwnPropertyNames', 'preventExtensions',
							'isExtensible', 'seal', 'isSealed', 'freeze',
							'isFrozen' ])('Date', Date, [ 'now' ]);

	Object.extend = extend.overloadSetter();

	Date.extend('now', function() {
		return +(new Date);
	});

	new Type('Boolean', Boolean);

	// fixes NaN returning as Number

	Number.prototype.$family = function() {
		return isFinite(this) ? 'number' : 'null';
	}.hide();

	// Number.random

	Number.extend('random', function(min, max) {
		return Math.floor(Math.random() * (max - min + 1) + min);
	});

	// forEach, each

	var hasOwnProperty = Object.prototype.hasOwnProperty;
	Object.extend('forEach', function(object, fn, bind) {
		for ( var key in object) {
			if (hasOwnProperty.call(object, key))
				fn.call(bind, object[key], key, object);
		}
	});

	Object.each = Object.forEach;

	Array.implement({

		forEach : function(fn, bind) {
			for (var i = 0, l = this.length; i < l; i++) {
				if (i in this)
					fn.call(bind, this[i], i, this);
			}
		},

		each : function(fn, bind) {
			Array.forEach(this, fn, bind);
			return this;
		}

	});

	// Array & Object cloning, Object merging and appending

	var cloneOf = function(item) {
		switch (typeOf(item)) {
		case 'array':
			return item.clone();
		case 'object':
			return Object.clone(item);
		default:
			return item;
		}
	};

	Array.implement('clone', function() {
		var i = this.length, clone = new Array(i);
		while (i--)
			clone[i] = cloneOf(this[i]);
		return clone;
	});

	var mergeOne = function(source, key, current) {
		switch (typeOf(current)) {
		case 'object':
			if (typeOf(source[key]) == 'object')
				Object.merge(source[key], current);
			else
				source[key] = Object.clone(current);
			break;
		case 'array':
			source[key] = current.clone();
			break;
		default:
			source[key] = current;
		}
		return source;
	};

	Object.extend({

		merge : function(source, k, v) {
			if (typeOf(k) == 'string')
				return mergeOne(source, k, v);
			for (var i = 1, l = arguments.length; i < l; i++) {
				var object = arguments[i];
				for ( var key in object)
					mergeOne(source, key, object[key]);
			}
			return source;
		},

		clone : function(object) {
			var clone = {};
			for ( var key in object)
				clone[key] = cloneOf(object[key]);
			return clone;
		},

		append : function(original) {
			for (var i = 1, l = arguments.length; i < l; i++) {
				var extended = arguments[i] || {};
				for ( var key in extended)
					original[key] = extended[key];
			}
			return original;
		}

	});

	// Object-less types

	[ 'Object', 'WhiteSpace', 'TextNode', 'Collection', 'Arguments' ]
			.each(function(name) {
				new Type(name);
			});

	// Unique ID

	var UID = Date.now();

	String.extend('uniqueID', function() {
		return (UID++).toString(36);
	});

})();
/*
 * ---
 * 
 * name: Object
 * 
 * description: Object generic methods
 * 
 * license: MIT-style license.
 * 
 * requires: Type
 * 
 * provides: [Object, Hash]
 * 
 * ...
 */

(function() {

	var hasOwnProperty = Object.prototype.hasOwnProperty;

	Object.extend({

		subset : function(object, keys) {
			var results = {};
			for (var i = 0, l = keys.length; i < l; i++) {
				var k = keys[i];
				if (k in object)
					results[k] = object[k];
			}
			return results;
		},

		map : function(object, fn, bind) {
			var results = {};
			for ( var key in object) {
				if (hasOwnProperty.call(object, key))
					results[key] = fn.call(bind, object[key], key, object);
			}
			return results;
		},

		filter : function(object, fn, bind) {
			var results = {};
			for ( var key in object) {
				var value = object[key];
				if (hasOwnProperty.call(object, key)
						&& fn.call(bind, value, key, object))
					results[key] = value;
			}
			return results;
		},

		every : function(object, fn, bind) {
			for ( var key in object) {
				if (hasOwnProperty.call(object, key)
						&& !fn.call(bind, object[key], key))
					return false;
			}
			return true;
		},

		some : function(object, fn, bind) {
			for ( var key in object) {
				if (hasOwnProperty.call(object, key)
						&& fn.call(bind, object[key], key))
					return true;
			}
			return false;
		},

		keys : function(object) {
			var keys = [];
			for ( var key in object) {
				if (hasOwnProperty.call(object, key))
					keys.push(key);
			}
			return keys;
		},

		values : function(object) {
			var values = [];
			for ( var key in object) {
				if (hasOwnProperty.call(object, key))
					values.push(object[key]);
			}
			return values;
		},

		getLength : function(object) {
			return Object.keys(object).length;
		},

		keyOf : function(object, value) {
			for ( var key in object) {
				if (hasOwnProperty.call(object, key) && object[key] === value)
					return key;
			}
			return null;
		},

		contains : function(object, value) {
			return Object.keyOf(object, value) != null;
		},

		toQueryString : function(object, base) {
			var queryString = [];

			Object.each(object, function(value, key) {
				if (base)
					key = base + '[' + key + ']';
				var result;
				switch (typeOf(value)) {
				case 'object':
					result = Object.toQueryString(value, key);
					break;
				case 'array':
					var qs = {};
					value.each(function(val, i) {
						qs[i] = val;
					});
					result = Object.toQueryString(qs, key);
					break;
				default:
					result = key + '=' + encodeURIComponent(value);
				}
				if (value != null)
					queryString.push(result);
			});

			return queryString.join('&');
		}

	});

})();

/*
 * ---
 * 
 * name: Array
 * 
 * description: Contains Array Prototypes like each, contains, and erase.
 * 
 * license: MIT-style license.
 * 
 * requires: Type
 * 
 * provides: Array
 * 
 * ...
 */

Array
		.implement({

			/* <!ES5> */
			every : function(fn, bind) {
				for (var i = 0, l = this.length >>> 0; i < l; i++) {
					if ((i in this) && !fn.call(bind, this[i], i, this))
						return false;
				}
				return true;
			},

			filter : function(fn, bind) {
				var results = [];
				for (var value, i = 0, l = this.length >>> 0; i < l; i++)
					if (i in this) {
						value = this[i];
						if (fn.call(bind, value, i, this))
							results.push(value);
					}
				return results;
			},

			indexOf : function(item, from) {
				var length = this.length >>> 0;
				for (var i = (from < 0) ? Math.max(0, length + from)
						: from || 0; i < length; i++) {
					if (this[i] === item)
						return i;
				}
				return -1;
			},

			map : function(fn, bind) {
				var length = this.length >>> 0, results = Array(length);
				for (var i = 0; i < length; i++) {
					if (i in this)
						results[i] = fn.call(bind, this[i], i, this);
				}
				return results;
			},

			some : function(fn, bind) {
				for (var i = 0, l = this.length >>> 0; i < l; i++) {
					if ((i in this) && fn.call(bind, this[i], i, this))
						return true;
				}
				return false;
			},
			/* </!ES5> */

			clean : function() {
				return this.filter(function(item) {
					return item != null;
				});
			},

			invoke : function(methodName) {
				var args = Array.slice(arguments, 1);
				return this.map(function(item) {
					return item[methodName].apply(item, args);
				});
			},

			associate : function(keys) {
				var obj = {}, length = Math.min(this.length, keys.length);
				for (var i = 0; i < length; i++)
					obj[keys[i]] = this[i];
				return obj;
			},

			link : function(object) {
				var result = {};
				for (var i = 0, l = this.length; i < l; i++) {
					for ( var key in object) {
						if (object[key](this[i])) {
							result[key] = this[i];
							delete object[key];
							break;
						}
					}
				}
				return result;
			},

			contains : function(item, from) {
				return this.indexOf(item, from) != -1;
			},

			append : function(array) {
				this.push.apply(this, array);
				return this;
			},

			getLast : function() {
				return (this.length) ? this[this.length - 1] : null;
			},

			getRandom : function() {
				return (this.length) ? this[Number.random(0, this.length - 1)]
						: null;
			},

			include : function(item) {
				if (!this.contains(item))
					this.push(item);
				return this;
			},

			combine : function(array) {
				for (var i = 0, l = array.length; i < l; i++)
					this.include(array[i]);
				return this;
			},

			erase : function(item) {
				for (var i = this.length; i--;) {
					if (this[i] === item)
						this.splice(i, 1);
				}
				return this;
			},

			empty : function() {
				this.length = 0;
				return this;
			},

			flatten : function() {
				var array = [];
				for (var i = 0, l = this.length; i < l; i++) {
					var type = typeOf(this[i]);
					if (type == 'null')
						continue;
					array = array
							.concat((type == 'array' || type == 'collection'
									|| type == 'arguments' || instanceOf(
									this[i], Array)) ? Array.flatten(this[i])
									: this[i]);
				}
				return array;
			},

			pick : function() {
				for (var i = 0, l = this.length; i < l; i++) {
					if (this[i] != null)
						return this[i];
				}
				return null;
			},

			hexToRgb : function(array) {
				if (this.length != 3)
					return null;
				var rgb = this.map(function(value) {
					if (value.length == 1)
						value += value;
					return value.toInt(16);
				});
				return (array) ? rgb : 'rgb(' + rgb + ')';
			},

			rgbToHex : function(array) {
				if (this.length < 3)
					return null;
				if (this.length == 4 && this[3] == 0 && !array)
					return 'transparent';
				var hex = [];
				for (var i = 0; i < 3; i++) {
					var bit = (this[i] - 0).toString(16);
					hex.push((bit.length == 1) ? '0' + bit : bit);
				}
				return (array) ? hex : '#' + hex.join('');
			}

		});

/*
 * ---
 * 
 * name: String
 * 
 * description: Contains String Prototypes like camelCase, capitalize, test, and
 * toInt.
 * 
 * license: MIT-style license.
 * 
 * requires: Type
 * 
 * provides: String
 * 
 * ...
 */

String.implement({

	test : function(regex, params) {
		return ((typeOf(regex) == 'regexp') ? regex : new RegExp('' + regex,
				params)).test(this);
	},

	contains : function(string, separator) {
		return (separator) ? (separator + this + separator).indexOf(separator
				+ string + separator) > -1 : String(this).indexOf(string) > -1;
	},

	trim : function() {
		return String(this).replace(/^\s+|\s+$/g, '');
	},

	clean : function() {
		return String(this).replace(/\s+/g, ' ').trim();
	},

	camelCase : function() {
		return String(this).replace(/-\D/g, function(match) {
			return match.charAt(1).toUpperCase();
		});
	},

	hyphenate : function() {
		return String(this).replace(/[A-Z]/g, function(match) {
			return ('-' + match.charAt(0).toLowerCase());
		});
	},

	capitalize : function() {
		return String(this).replace(/\b[a-z]/g, function(match) {
			return match.toUpperCase();
		});
	},

	escapeRegExp : function() {
		return String(this).replace(/([-.*+?^${}()|[\]\/\\])/g, '\\$1');
	},

	toInt : function(base) {
		return parseInt(this, base || 10);
	},

	toFloat : function() {
		return parseFloat(this);
	},

	hexToRgb : function(array) {
		var hex = String(this).match(/^#?(\w{1,2})(\w{1,2})(\w{1,2})$/);
		return (hex) ? hex.slice(1).hexToRgb(array) : null;
	},

	rgbToHex : function(array) {
		var rgb = String(this).match(/\d{1,3}/g);
		return (rgb) ? rgb.rgbToHex(array) : null;
	},

	substitute : function(object, regexp) {
		return String(this).replace(regexp || (/\\?\{([^{}]+)\}/g),
				function(match, name) {
					if (match.charAt(0) == '\\')
						return match.slice(1);
					return (object[name] != null) ? object[name] : '';
				});
	}

});

/*
 * ---
 * 
 * name: Function
 * 
 * description: Contains Function Prototypes like create, bind, pass, and delay.
 * 
 * license: MIT-style license.
 * 
 * requires: Type
 * 
 * provides: Function
 * 
 * ...
 */

Function.extend({

	attempt : function() {
		for (var i = 0, l = arguments.length; i < l; i++) {
			try {
				return arguments[i]();
			} catch (e) {
			}
		}
		return null;
	}

});

Function.implement({

	attempt : function(args, bind) {
		try {
			return this.apply(bind, Array.from(args));
		} catch (e) {
		}

		return null;
	},

	/* <!ES5-bind> */
	bind : function(that) {
		var self = this, args = arguments.length > 1 ? Array
				.slice(arguments, 1) : null, F = function() {
		};

		var bound = function() {
			var context = that, length = arguments.length;
			if (this instanceof bound) {
				F.prototype = self.prototype;
				context = new F;
			}
			var result = (!args && !length) ? self.call(context) : self.apply(
					context, args && length ? args.concat(Array
							.slice(arguments)) : args || arguments);
			return context == that ? result : context;
		};
		return bound;
	},
	/* </!ES5-bind> */

	pass : function(args, bind) {
		var self = this;
		if (args != null)
			args = Array.from(args);
		return function() {
			return self.apply(bind, args || arguments);
		};
	},

	delay : function(delay, bind, args) {
		return setTimeout(this.pass((args == null ? [] : args), bind), delay);
	},

	periodical : function(periodical, bind, args) {
		return setInterval(this.pass((args == null ? [] : args), bind),
				periodical);
	}

});

/*
 * ---
 * 
 * name: Number
 * 
 * description: Contains Number Prototypes like limit, round, times, and ceil.
 * 
 * license: MIT-style license.
 * 
 * requires: Type
 * 
 * provides: Number
 * 
 * ...
 */

Number.implement({

	limit : function(min, max) {
		return Math.min(max, Math.max(min, this));
	},

	round : function(precision) {
		precision = Math.pow(10, precision || 0).toFixed(
				precision < 0 ? -precision : 0);
		return Math.round(this * precision) / precision;
	},

	times : function(fn, bind) {
		for (var i = 0; i < this; i++)
			fn.call(bind, i, this);
	},

	toFloat : function() {
		return parseFloat(this);
	},

	toInt : function(base) {
		return parseInt(this, base || 10);
	}

});

Number.alias('each', 'times');

(function(math) {
	var methods = {};
	math.each(function(name) {
		if (!Number[name])
			methods[name] = function() {
				return Math[name].apply(null, [ this ].concat(Array
						.from(arguments)));
			};
	});
	Number.implement(methods);
})([ 'abs', 'acos', 'asin', 'atan', 'atan2', 'ceil', 'cos', 'exp', 'floor',
		'log', 'max', 'min', 'pow', 'sin', 'sqrt', 'tan' ]);

/*
 * ---
 * 
 * name: Class
 * 
 * description: Contains the Class Function for easily creating, extending, and
 * implementing reusable Classes.
 * 
 * license: MIT-style license.
 * 
 * requires: [Array, String, Function, Number]
 * 
 * provides: Class
 * 
 * ...
 */

(function() {

	var Class = this.Class = new Type('Class', function(params) {
		if (instanceOf(params, Function))
			params = {
				initialize : params
			};

		var newClass = function() {
			reset(this);
			if (newClass.$prototyping)
				return this;
			this.$caller = null;
			var value = (this.initialize) ? this.initialize.apply(this,
					arguments) : this;
			this.$caller = this.caller = null;
			return value;
		}.extend(this).implement(params);

		newClass.$constructor = Class;
		newClass.prototype.$constructor = newClass;
		newClass.prototype.parent = parent;

		return newClass;
	});

	var parent = function() {
		if (!this.$caller)
			throw new Error('The method "parent" cannot be called.');
		var name = this.$caller.$name, parent = this.$caller.$owner.parent, previous = (parent) ? parent.prototype[name]
				: null;
		if (!previous)
			throw new Error('The method "' + name + '" has no parent.');
		return previous.apply(this, arguments);
	};

	var reset = function(object) {
		for ( var key in object) {
			var value = object[key];
			switch (typeOf(value)) {
			case 'object':
				var F = function() {
				};
				F.prototype = value;
				object[key] = reset(new F);
				break;
			case 'array':
				object[key] = value.clone();
				break;
			}
		}
		return object;
	};

	var wrap = function(self, key, method) {
		if (method.$origin)
			method = method.$origin;
		var wrapper = function() {
			if (method.$protected && this.$caller == null)
				throw new Error('The method "' + key + '" cannot be called.');
			var caller = this.caller, current = this.$caller;
			this.caller = current;
			this.$caller = wrapper;
			var result = method.apply(this, arguments);
			this.$caller = current;
			this.caller = caller;
			return result;
		}.extend({
			$owner : self,
			$origin : method,
			$name : key
		});
		return wrapper;
	};

	var implement = function(key, value, retain) {
		if (Class.Mutators.hasOwnProperty(key)) {
			value = Class.Mutators[key].call(this, value);
			if (value == null)
				return this;
		}

		if (typeOf(value) == 'function') {
			if (value.$hidden)
				return this;
			this.prototype[key] = (retain) ? value : wrap(this, key, value);
		} else {
			Object.merge(this.prototype, key, value);
		}

		return this;
	};

	var getInstance = function(klass) {
		klass.$prototyping = true;
		var proto = new klass;
		delete klass.$prototyping;
		return proto;
	};

	Class.implement('implement', implement.overloadSetter());

	Class.Mutators = {

		Extends : function(parent) {
			this.parent = parent;
			this.prototype = getInstance(parent);
		},

		Implements : function(items) {
			Array.from(items).each(function(item) {
				var instance = new item;
				for ( var key in instance)
					implement.call(this, key, instance[key], true);
			}, this);
		}
	};

})();

/*
 * ---
 * 
 * name: Class.Extras
 * 
 * description: Contains Utility Classes that can be implemented into your own
 * Classes to ease the execution of many common tasks.
 * 
 * license: MIT-style license.
 * 
 * requires: Class
 * 
 * provides: [Class.Extras, Chain, Events, Options]
 * 
 * ...
 */

(function() {

	this.Chain = new Class({

		$chain : [],

		chain : function() {
			this.$chain.append(Array.flatten(arguments));
			return this;
		},

		callChain : function() {
			return (this.$chain.length) ? this.$chain.shift().apply(this,
					arguments) : false;
		},

		clearChain : function() {
			this.$chain.empty();
			return this;
		}

	});

	var removeOn = function(string) {
		return string.replace(/^on([A-Z])/, function(full, first) {
			return first.toLowerCase();
		});
	};

	this.Events = new Class({

		$events : {},

		addEvent : function(type, fn, internal) {
			type = removeOn(type);

			this.$events[type] = (this.$events[type] || []).include(fn);
			if (internal)
				fn.internal = true;
			return this;
		},

		addEvents : function(events) {
			for ( var type in events)
				this.addEvent(type, events[type]);
			return this;
		},

		fireEvent : function(type, args, delay) {
			type = removeOn(type);
			var events = this.$events[type];
			if (!events)
				return this;
			args = Array.from(args);
			events.each(function(fn) {
				if (delay)
					fn.delay(delay, this, args);
				else
					fn.apply(this, args);
			}, this);
			return this;
		},

		removeEvent : function(type, fn) {
			type = removeOn(type);
			var events = this.$events[type];
			if (events && !fn.internal) {
				var index = events.indexOf(fn);
				if (index != -1)
					delete events[index];
			}
			return this;
		},

		removeEvents : function(events) {
			var type;
			if (typeOf(events) == 'object') {
				for (type in events)
					this.removeEvent(type, events[type]);
				return this;
			}
			if (events)
				events = removeOn(events);
			for (type in this.$events) {
				if (events && events != type)
					continue;
				var fns = this.$events[type];
				for (var i = fns.length; i--;)
					if (i in fns) {
						this.removeEvent(type, fns[i]);
					}
			}
			return this;
		}

	});

	this.Options = new Class({

		setOptions : function() {
			var options = this.options = Object.merge.apply(null, [ {},
					this.options ].append(arguments));
			if (this.addEvent)
				for ( var option in options) {
					if (typeOf(options[option]) != 'function'
							|| !(/^on[A-Z]/).test(option))
						continue;
					this.addEvent(option, options[option]);
					delete options[option];
				}
			return this;
		}

	});

})();


/**
 * @class 基类
 * @description 基类，供基于mootools自定义类继承用，把定义类时的共同部分抽象出来，以便方便继承。
 * @version 1.0
 */
var Base = new Class({
	Implements : [ Options, Events ],

	initialize : function(options) {
		this.setOptions(options);
	}
});

/**
 * 汇合工具类
 */
UtilMisc = (function() {

	var loadJSForSync = function(isDebug, url) {
		if (isDebug) {
			jQuery.ajax({
				type : "POST",
				async : false,
				url : url,
				dataType : "script",
				cache : false
			});
		} else {
			var id=url.replace(/\//gm, '').replace(/\./gm, '');
			if ($("#" + id).length <= 0) {
				jQuery.ajax({
					type : "POST",
					async : false,
					url : url,
					dataType : "script",
					cache : false,
					success : function(data) {
						$('<input>',{
							id:id,
							type:'hidden'
						}).appendTo(document.body);
					}
				});
			}

		}
	};

	var loadJSForAsync = function(url) {
		var id=url.replace(/\//gm, '').replace(/\./gm, '');
		if ($("#" + id).length <= 0) {
			// 取得<head> Dom元素
			var oHead = document.getElementsByTagName("head").item(0);
			// 创建<script>元素，并设置个项属性
			var oScript = document.createElement("script");
			oScript.id = id;
			oScript.type = "text/javascript";
			oScript.src = url;

			// 将<script>元素追加到<head>元素中
			oHead.appendChild(oScript);
		}
	};

	return {

		/**
		 * 获取随机数
		 */
		getRandom : function() {
			if (arguments[0]) {
				return Math.floor(Math.random() * arguments[0] + 1);
			}
			if (arguments[1]) {
				return parseInt(Math.random() * (arguments[0] - arguments[1])
						+ arguments[0]);
			}
		},

		/**
		 * 获取IFrame的DOM对象
		 */
		getIFrameDocument : function(id) {
			return document.getElementById(id).contentDocument
					|| document.frames[id].document;
		},

		/**
		 * 获取IFrame的window对象
		 */
		getIFrameWindow : function(id) {
			return document.getElementById(id).contentWindow
					|| document.frames[id].window;
		},

		/**
		 * 设置Cookie
		 * 
		 * @param name
		 *            cookie名字
		 * @param value
		 *            cookie值
		 * 
		 */
		setCookie : function(name, value) {
			var Days = 30; // 此 cookie 将被保存 30 天
			var exp = new Date(); // new Date("December 31, 9998");
			exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
			
			document.cookie = name + "=" + escape(value) + ";expires="
					+ exp.toGMTString()+";path=/";
		},

		/**
		 * 获取Cookie
		 * 
		 * @param name
		 *            cookie名字
		 * @returns cookie值
		 */
		getCookie : function(name) {
			var arrStr = document.cookie.split("; ");
			for (var i = 0; i < arrStr.length; i++) {
				var temp = arrStr[i].split("=");
				if (temp[0] == name)
					return unescape(temp[1]);
			}
		},

		/**
		 * 删除cookie
		 * 
		 * @param name
		 *            cookie名字
		 */
		removeCookie : function(name) {
			var expires = new Date();
			expires.setTime(expires.getTime() - 1000 * 60);
			this.setCookie(name, "", expires);
		},

		/**
		 * 加载JS
		 */
		loadJS : function(url,isDebug,isSync) {
			if (isSync==undefined||isSync==true) {
				loadJSForSync(isDebug, url);
			} else {
				loadJSForAsync(url);
			}
		},
		
		/**
		 *创建命名空间 
		 */
		namespace:function(){
            var o, d;
            arguments.each(function(v) {
                d = v.split(".");
                o = window[d[0]] = window[d[0]] || {};
                Ext.each(d.slice(1), function(v2){
                    o = o[v2] = o[v2] || {};
                });
            });
            return o;
		},
		
		/**
		 * 获取URL参数
		 */
		getQueryString:function(name){
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null){
				//return unescape(r[2]); 
				return decodeURI(r[2]); 
			} 
			return null;
			//eval('(' + str + ')');
		},
		 getContextPath:function() {
			var host = window.location.host;
			var pathName = document.location.pathname;
			var index = pathName.substr(1).indexOf("/");
			var result = "http://"+host+pathName.substr(0,index+1);
			return result;
		},
		getRelativePath:function(){
			var host = window.location.host;
			var pathName = document.location.pathname;
			var index = pathName.substr(1).indexOf("/");
			var endIndex = pathName.substr(1).indexOf("alMap");
			var result = pathName.substr(index+1,endIndex-1);
			return result;
		},
		getCurrentTime:function(){
			var myDate = new Date();
			return myDate.getTime();
		}
		/*var myDate = new Date();
myDate.getYear();        //获取当前年份(2位)
myDate.getFullYear();    //获取完整的年份(4位,1970-????)
myDate.getMonth();       //获取当前月份(0-11,0代表1月)
myDate.getDate();        //获取当前日(1-31)
myDate.getDay();         //获取当前星期X(0-6,0代表星期天)
myDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
myDate.getHours();       //获取当前小时数(0-23)
myDate.getMinutes();     //获取当前分钟数(0-59)
myDate.getSeconds();     //获取当前秒数(0-59)
myDate.getMilliseconds();    //获取当前毫秒数(0-999)
myDate.toLocaleDateString();     //获取当前日期
var mytime=myDate.toLocaleTimeString();     //获取当前时间
myDate.toLocaleString( );        //获取日期与时间
*/
	};
})();


/**
 * 对String进行扩展
 */
String.implement({
	bytes:function(){
	  var num=this.length;
	  var arr=this.match(/[^\x00-\x80]/ig); 
	  if(arr!=null) 
	      num+=arr.length;
	  return num;
	},
	get:function(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = this.match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
});

