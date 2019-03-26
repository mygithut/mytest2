/**
 * TabPanel Javascript Component v2.0
 * 
 * Copyright 2010, Marker King
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * 
 * Build on jQuery JavaScript Library v1.4.4
 * 
 * Date: 2010.12.22
 * 
 * $ protected
 * _ private
 * 
 * @param {Object}   config
 * 
 * {string | jQuery} config.renderTo
 * {string | number} config.width
 * {string | number} config.height
 * {Array}           config.items
 * {boolean}         config.border
 * {string | number} config.defaultTab
 * {number}          config.maxTab
 * {number}          config.moveSize
 * {boolean}         config.autoResize
 */
var TabPanel = (function(){
  return function(config){
    this.id         = 'jTabPanel-'+config.id;
    this.renderTo   = config.renderTo || $(document.body);
    this.render     = typeof this.renderTo === 'string' ? $('#'+this.renderTo) : this.renderTo;
    this.width      = config.width || '100%';
    this.height     = config.height || '100%';
    this.items      = config.items;
    this.border     = config.border;
    this.defaultTab = config.defaultTab;
    this.maxTab     = config.maxTab;
    this.moveSize   = config.moveSize || 100;
    this.autoResize = config.autoResize;
		this.icon       = config.icon;
    //˽������
    this.$tabs      = {};
    this.$tabsArray = [];
    this.$maxMove   = 0;
    this.$scrolled  = false;
    this.$scrollFinished = true;
    this._init();
  }
})();

TabPanel.prototype = {
  /**
   * ��ʼ��
   */
  _init: function(){
    var $tabEntity = this;

    if(this.autoResize) {
      this.width = '100%';
      this.height = '100%';
      //IE�ᴥ������resize��FF3�������������õ��϶���һֱ�ڴ���resize��������Timer������
      var _resizeTimer;
      $(window).bind('resize.tabpanel', function(){
        window.clearTimeout(_resizeTimer);
        _resizeTimer = window.setTimeout(function(){
          $tabEntity.resize();
        },300);
      });
    }
    //���HTML
    var _tabHTML = [];
    _tabHTML.push('<div class="jTabPanel" id="' + this.id + '">');//TabPanel start
    _tabHTML.push('<div class="tab-contrl-wrap">');//tab contrl wrap start
    _tabHTML.push('<div class="tab-scroll tab-left-button">&lt;</div>');//tab left button
    _tabHTML.push('<div class="tab-scroll tab-right-button">&gt;</div>');//tab right button
    _tabHTML.push('<div class="tab-item-wrap">');//tab item wrap start
    _tabHTML.push('<ul class="tab-item-move"></ul>');
    _tabHTML.push('</div>');//tab item wrap end
    _tabHTML.push('<div class="panel-spacer"></div>');//space line
    _tabHTML.push('</div>');//tab contrl wrap end
    _tabHTML.push('<div class="panel-content"></div>');//HTML content
    _tabHTML.push('</div>');//TabPanel end
    this.render.append(_tabHTML.join(''));
    
    //���DOM
    this.jTabPanel     = $('#' + this.id);
    this.tabContrlWrap = $('#' + this.id + ' .tab-contrl-wrap');
    this.tabItemWrap   = $('#' + this.id + ' .tab-item-wrap');
    this.tabItemMove   = $('#' + this.id + ' .tab-item-move');
    this.tabContent    = $('#' + this.id + ' .panel-content');
    this.leftButton    = $('#' + this.id + ' .tab-left-button').select(function(){return false;});
    this.rightButton   = $('#' + this.id + ' .tab-right-button').select(function(){return false;});
    
    var _timer;
    //���ҹ����¼�
    this.leftButton.bind({
      click: function(){
        window.clearTimeout(_timer);
        _timer = window.setTimeout(function(){
          $tabEntity._move('+', $tabEntity.moveSize);
        }, 200);
      },
      dblclick: function(){
        window.clearTimeout(_timer);
        $tabEntity._move('+', Math.abs(parseInt($tabEntity.tabItemMove.css('margin-left'), 10)));
      } 
    });
    this.rightButton.bind({
      click: function(){
        window.clearTimeout(_timer);
        _timer = window.setTimeout(function(){
          $tabEntity._move('-', $tabEntity.moveSize);
        }, 200);
      },
      dblclick: function(){
        window.clearTimeout(_timer);
        $tabEntity._move('-', $tabEntity.$maxMove + parseInt($tabEntity.tabItemMove.css('margin-left'), 10));
      }
    });
    
    //�ж��Ƿ���border
    if (this.border) {
      this.jTabPanel.addClass('jTabPanelBorder');
    }
    this.update();
    //ѭ�����item
    for (var i = 0; i < this.items.length; i++) {
      this.addTab(this.items[i]);
    }
    //��Ĭ��ѡ�
    if (this.defaultTab != undefined) {
      switch (typeof this.defaultTab) {
        case 'string':
          this.show(this.defaultTab);
          break;
        case 'number':
          this.show(this._getItemObject(this.defaultTab).id);
          break;
      }
    }
  },
  /**
   * ����
   */
  update: function(){
    //����border��Ⱥ͸߶�
    this.border_w = this.jTabPanel.outerWidth() - this.jTabPanel.width();
    this.border_h = this.jTabPanel.outerHeight() - this.jTabPanel.height();
    //��������߶ȺͿ�ȣ���Ҫ�������ã���ֹ�ٷֱȱ���
    this.jTabPanel.css({
      width: this.width,
      height: this.height
    }).css({
      width: this.jTabPanel.width() - this.border_w,
      height: this.jTabPanel.height() - this.border_h
    });
    //�������ݸ߶�
    this.tabContent.height(this.jTabPanel.outerHeight() - this.tabContrlWrap.outerHeight() - this.border_h);
    this._updateWhere();
  },
  /**
   * ���ѡ�
   * @param {Object} item
   * 
   * {string}  item.id
   * {string}  item.title
   * {number}  item.width
   * {string}  item.icon
   * {boolean} item.closable
   * {boolean} item.lazyload
   */
  addTab: function(item){
    var $tabEntity = this;
    if(!this.$scrollFinished) {
      return;
    }
    //�ж��Ƿ�����tab
    if (this.$tabs[item.id]) {
      this.show(item.id);
      return;
    } else if (this.maxTab <= this.$tabsArray.length) {
      alert('���������������ܴ�');
      return; 
    }
    //���HTML
    var _itemHTML = [];
    _itemHTML.push('<li id="' + item.id + '">');
    if (item.position) {
			item.position.a = item.position.a || '';
			item.position.b = item.position.b || item.position.a;
      _itemHTML.push('<div class="tab-item-title-icon" style="background-image: url(' + this.icon + ');"></div>');
      _itemHTML.push('<div class="tab-item-title tab-item-title-nopadding">');
    } else {
			item.position = {};
			item.position.a = item.position.b = '';
      _itemHTML.push('<div class="tab-item-title">');
		}
    _itemHTML.push(item.title);
    _itemHTML.push('</div>');
    _itemHTML.push('</li>');
    this.tabItemMove.append(_itemHTML.join(''));
    
    item.closable = item.closable || false;
    item.content = $('<div class="content-wrap"></div>');
    item.tabTitle = $('#' + item.id);
    item.tabTitleText = $('.tab-item-title', item.tabTitle);
		item.tabTitleIcon = $('.tab-item-title-icon', item.tabTitle);
		item.tabTitleIcon.css('background-position', item.position.a);
    this.tabContent.append(item.content);
    
    //���¼�
    item.tabTitle.click(function(){
      $tabEntity.show(item.id);
    })
    this._updateTabItem(item);
    //����ǰ�ÿ�
    item.pretab = this.active;
    //����������
    this.$tabsArray.push({
      id: item.id
    });
    //��itemд�������
    this.$tabs[item.id] = item;
    //����
    this._updateWhere();
    this._showScroll();
    //��ʾ
    if (!item.lazyload) {
      this.show(item.id);
    }
  },
  /**
   * ���¿�������ʹ��
   * @param {Object} item ͬ��Item
   */
  _updateTabItem: function(item){
    var $tabEntity = this;
    //���ÿ��
    item.tabTitleText.width('');
    //�������
    var _fixWidth = 0;
    //�ָ�δ��״̬
    item.tabTitle.unbind('dblclick');
    //�ɹرգ����Ӳ�����ȣ��󶨹ر��¼�
    if (item.closable) {
      //���йرհ�ť�Ļ���ɾ������¼�
      if (item.closer) {
        item.closer.unbind('click');
      } else { //��Ӱ�ť
        item.closer = $('<div class="tab-closer"></div>');
        item.tabTitle.append(item.closer);
      }
      //���㲹�����
      _fixWidth = item.closer.outerWidth(true) + parseInt(item.closer.css('right'), 10);
      //���¼�
      item.closer.click(function(){
        $tabEntity.removeTab(item.id);
      });
      item.tabTitle.dblclick(function(){
        $tabEntity.removeTab(item.id);
      });
    } else { //����йرհ�ť��ɾ���رհ�ť
      if (item.closer) {
        item.closer.remove();
        delete item['closer'];
      }
    }
		//����icon�������
		if(item.position) {
			_fixWidth += item.tabTitleIcon.width();
		}
    //����title�Ŀ�Ȳ�ֵ
    var _subWidth = item.tabTitleText.outerWidth(true) - item.tabTitleText.width();
    //��������˿��
    if (item.width) {
      //�ȸ�LI���ÿ��
      item.tabTitle.width(item.width);
      //�ж��Ƿ񳬳����
      if (item.tabTitleText.outerWidth(true)  > (item.width - _fixWidth)) {
        //����...����
        var _dot = $('<div class="fix">...</div>');
        item.tabTitleText.after(_dot);
        //�������ֲ��ȣ��ܿ��-�������-���ֲ��Ȳ�ֵ��
        _fixWidth += _dot.outerWidth(true);
        item.tabTitleText.width(item.width - _fixWidth - _subWidth);
        //����title
        item.tabTitle.attr('title', item.title);
      } else { //�Ƴ�...����
        item.tabTitle.children('.fix').remove();
        item.tabTitle.removeAttr('title');
      }
    } else {
      item.tabTitle.width(item.tabTitleText.outerWidth(true) + _fixWidth);
    }
  },
  /**
   * ��ʾѡ�
   * @param {string} tabId
   */
  show: function(tabId){
    tabId = this._getItemId(tabId);
    //�Ƿ��Ѿ���ʾ
    if (this.active === tabId) {
      return;
    }
    //�ж��Ƿ��и�ID�Ŀ�
    if (this.$tabs[tabId]) {
      //����active����ʾ����
      if (this.$tabs[this.active]) {
        this.$tabs[this.active].tabTitle.removeClass('active');
        this.$tabs[this.active].content.css('display', 'none');
				this.$tabs[this.active].tabTitleIcon.css('background-position', this.$tabs[this.active].position.a);
      }
      this.$tabs[tabId].tabTitle.addClass('active');
      this.$tabs[tabId].content.css('display', 'block');
			this.$tabs[tabId].tabTitleIcon.css('background-position', this.$tabs[tabId].position.b);
      this.active = tabId;
      //�ӳټ����ж��Ƿ��Ѽ���
      if (this.$tabs[tabId].content.html() === '') {
        this.$tabs[tabId].content.html((this.$tabs[tabId].htmlObject = $(this.$tabs[tabId].html)));
        if($.browser.msie && $.browser.version === '6.0') {
          this.$tabs[tabId].htmlObject.attr('src', this.$tabs[tabId].htmlObject.attr('src'));
        }
      }
    } else {
      alert('ID not found.');
    }
    this.moveToVisible();
  },
  /**
   * �ر�ѡ�
   * @param {string} tabId
   */
  removeTab: function(tabId){
    tabId = this._getItemId(tabId);
    var _pretab = this.$tabs[tabId].pretab;
    //��DOM��ɾ��
    this.$tabs[tabId].tabTitle.empty();
    this.$tabs[tabId].tabTitle.remove();
    this.$tabs[tabId].content.empty();
    this.$tabs[tabId].content.remove();
    //ɾ������
    delete this.$tabs[tabId];
    for (var i = 0; i < this.$tabsArray.length; i++) {
      if (this.$tabsArray[i].id === tabId) {
        this.$tabsArray.splice(i, 1);
        this.update();
        this._showScroll();
        break;
      }
    }
    //������رտ����ǵ�ǰ��
    if (this.active === tabId) {
      //��ʾǰ�ÿ�
      if (_pretab && this.$tabs[_pretab]) {
        this.show(_pretab);
      } else if (this.$tabsArray.length > 0) {
        this.show(this._getItemObject(0).id);
      }
    } else { //�ƶ����ɼ�
      this.moveToVisible();
    }
    this._updateWhere();
    this._showScroll();
  },
  /**
   * ����ÿ��������λ�ã���������ƶ���
   */
  _updateWhere: function(){
    this.$maxMove = 0;
    for (var i = 0; i < this.$tabsArray.length; i++) {
      this.$maxMove += this.$tabs[this.$tabsArray[i].id].tabTitle.outerWidth(true);
      this.$tabsArray[i].where = this.$maxMove;
    }
    if(this.$scrolled) {
      //��ȥ����margin�Ŀ��
      var _lm = this.leftButton.outerWidth(true);
      var _rm = this.rightButton.outerWidth(true) + parseInt(this.tabItemWrap.css('padding-right'), 10);
      this.tabItemWrap.width(this.tabContrlWrap.width() - _lm - _rm);
    } else {
      this.tabItemWrap.width(this.tabContrlWrap.width());
    }
    this.$maxMove -= this.tabItemWrap.width();
  },
  /**
   * ��ʾ������
   */
  _showScroll: function(){
    //������һ��ѡ�����λ��
    var _liWhere = this.$tabsArray[this.$tabsArray.length - 1].where;
    //��ÿ��Ʋ�Ŀ��
    var _contrlWidth = this.tabContrlWrap.width();
    //�������Ʋ㣬����δ��ʾ������
    if (_liWhere > _contrlWidth && !this.$scrolled) {
      this.tabItemWrap.addClass('tab-item-wrap-scroll');
      this.leftButton.addClass('show');
      this.rightButton.addClass('show');
      this.$scrolled = true;
      this._updateWhere();
    } else if (_contrlWidth > _liWhere && this.$scrolled) { //Ϊ�������Ʋ㣬��������ʾ������
      this.tabItemWrap.removeClass('tab-item-wrap-scroll');
      this.leftButton.removeClass('show');
      this.rightButton.removeClass('show');
      this.$scrolled = false;
      this._updateWhere();
    }
  },
  /**
   * �жϹ����������Ƿ����
   */
  _useableScroll: function(){
    this.$scrollFinished = !this.$scrollFinished;
    if (this.$scrolled) {
      var _itemWrapWhere = parseInt(this.tabItemMove.css('margin-left'), 10);
      //����
      if (_itemWrapWhere >= 0) {
        this.leftButton.attr('disabled', true).addClass('tab-button-disabled');
        this.rightButton.removeAttr('disabled').removeClass('tab-button-disabled');
      } else if (Math.abs(_itemWrapWhere) >= this.$maxMove) { //�Ҳ���
        this.leftButton.removeAttr('disabled').removeClass('tab-button-disabled');
        this.rightButton.attr('disabled', true).addClass('tab-button-disabled');
      } else { //ȫ��
        this.leftButton.removeAttr('disabled').removeClass('tab-button-disabled');
        this.rightButton.removeAttr('disabled').removeClass('tab-button-disabled');
      }
    }
  },
  /**
   * �ƶ�
   * @param {string} operator +|-
   * @param {number} m
   */
  _move: function(operator, m){
    var $tabEntity = this;
    if(!this.$scrollFinished) {
      return;
    }
    //��õ�ǰλ��
    var _nowWhere = parseInt(this.tabItemMove.css('margin-left'), 10);
    //�ж�+��-
    if (operator === '+') {
      //Ϊ0ֱ�ӷ���
      if (_nowWhere === 0) {
        return;
      } else if ((_nowWhere + m) > 0) { //�����ƶ�λ�ú�������0
        //�ƶ���Ϊ����ֵ
        m = Math.abs(_nowWhere);
      }
    } else if (operator === '-') {
      //��ǰλ�þ���ֵ+�ƶ���������������ƶ���
      if ((Math.abs(_nowWhere) + m) > this.$maxMove) {
        //�ƶ���Ϊ��ֵ��_nowWhereΪ������
        m = this.$maxMove + _nowWhere;
      }
    }
    //�ƶ�������0ʱ��ִ���ƶ�
    if (m > 0) {
      this.$scrollFinished = !this.$scrollFinished;
      this.tabItemMove.animate({
        'margin-left': operator + '=' + m
      }, 300, function(){
        $tabEntity._useableScroll();
      });
    }
  },
  /**
   * ֱ���ƶ������ʵĿɼ�λ��
   */
  moveToVisible: function(){
    //���move�㵱ǰ��margin
    var _movePosition = parseInt(this.tabItemMove.css('margin-left'), 10);
    //��õ�ǰ���
    var _activeTab = this.$tabs[this.active];
    //��õ�ǰ���������
    var _activeTabProperty = this._getItemProperty(this.active);
    //��ÿ����λ��
    var _activeTabLeftPosition = _activeTabProperty.where - _activeTab.tabTitle.outerWidth(true) + _movePosition;
    //��ÿ��Ҳ�λ��
    var _activeTabRightPosition = _activeTabProperty.where + _movePosition;
    //��ÿ���Χ��Ŀ��
    var _itemWrapWidth = this.tabItemWrap.width();
    //����ʾ������
    if (this.$scrolled) {
      //�������ڷ�Χ��
      if (_activeTabLeftPosition < 0) {
        //������һ�������Ҳ�λ��+�ƶ����λ�û�δ�������Ҷ�
        if (this.$tabsArray[this.$tabsArray.length - 1].where + _movePosition + Math.abs(_activeTabLeftPosition) < _itemWrapWidth) {
          this._move('+', _itemWrapWidth - (this.$tabsArray[$tabsArray.length - 1].where + _movePosition));
        } else { //�ƶ����ɼ�λ��
          this._move('+', Math.abs(_activeTabLeftPosition));
        }
      } else if (_activeTabRightPosition > _itemWrapWidth) { //����Ҳ��ڷ�Χ��
        this._move('-', _activeTabRightPosition - _itemWrapWidth);
      } else if (this.$tabsArray[this.$tabsArray.length - 1].where + _movePosition < _itemWrapWidth) { //���Ҷ��ڷ�Χ�ڣ������һ�����Ҳ�δ�ﵽ���Ҷ�
        this._move('+', _itemWrapWidth - (this.$tabsArray[this.$tabsArray.length - 1].where + _movePosition));
      }
    } else {
      //�������ڷ�Χ��
      if (_movePosition < 0) {
        //�ƶ��������
        this._move('+', Math.abs(_movePosition));
      }
    }
  },
  /**
   * ���ItemID
   * @param {string | number} tabId
   */
  _getItemId: function(tabId){
    if (typeof tabId === 'number') {
      tabId = this._getItemObject(tabId).id;
    }
    return tabId;
  },
  /**
   * ���Item������
   * @param {string} tabId
   */
  _getItemProperty: function(tabId){
    for (var i = 0; i < this.$tabsArray.length; i++) {
      if (this.$tabsArray[i].id === tabId) {
        return this.$tabsArray[i];
      }
    }
  },
  /**
   * ���Item����
   * @param {number} index
   */
  _getItemObject: function(index){
    return this.$tabs[this.$tabsArray[index].id];
  },
  /**
   * ��ÿ�Title
   * @param {string | number} tabId
   */
  getTitle: function(tabId){
    tabId = this._getItemId(tabId);
    return this.$tabs[tabId].tabTitle.children('.tab-item-title').text();
  },
  /**
   * ���ÿ�Title
   * @param {string | number} tabId
   * @param {string}          title
   */
  setTitle: function(tabId, title){
    tabId = this._getItemId(tabId);
    if (this.$tabs[tabId].title === title) {
      return;
    }
    this.$tabs[tabId].title = title;
    this.$tabs[tabId].tabTitle.children('.tab-item-title').text(title);
    this._updateTabItem(this.$tabs[tabId]);
		this._updateWhere();
		this.moveToVisible();
  },
  /**
   * ��ÿ����
   * @param {string | number} tabId
   */
  getWidth: function(tabId){
    tabId = this._getItemId(tabId);
    return this.$tabs[tabId].width;
  },
  /**
   * ���ÿ����
   * @param {string | number} tabId
   * @param {string | number} width
   */
  setWidth: function(tabId, width){
    tabId = this._getItemId(tabId);
    if (this.$tabs[tabId].width === width) {
      return;
    }
    this.$tabs[tabId].width = width;
    this._updateTabItem(this.$tabs[tabId]);
    this._updateWhere();
		this.moveToVisible();
  },
  /**
   * ��ÿ��Ƿ�ɹر�
   * @param {string | number} tabId
   */
  getClosable: function(tabId){
    tabId = this._getItemId(tabId);
    return this.$tabs[tabId].closable;
  },
  /**
   * ���ÿ��Ƿ�ɹر�
   * @param {string | number} tabId
   * @param {boolean}         closable
   */
  setClosable: function(tabId, closable){
    tabId = this._getItemId(tabId);
    if (this.$tabs[tabId].closable === closable) {
      return;
    }
    this.$tabs[tabId].closable = closable;
    this._updateTabItem(this.$tabs[tabId]);
    this._updateWhere();
		this.moveToVisible();
  },
  /**
   * ���ø߶ȿ�ȣ��ɴ�null
   * @param {number} width
   * @param {number} height
   */
  resize : function(width, height) {
    this.width = width || this.width;
    this.height = height || this.height;
    this.update();
    this._showScroll();
    this.moveToVisible();
  },
  /**
   * ˢ��ѡ�
   * @param {string | number} tabId
   */
  flush : function(tabId) {
    //�õ��±�
    tabId = this._getItemId(tabId);
    //���û�и�ѡ�,��ִ��ˢ��
    if(!this.$tabs[tabId]) {
      return false;
    } else {
      var iframes = this.$tabs[tabId].content.find('iframe');
      if(iframes.length > 0) {
        var frameId = this.$tabs[tabId].id+'Frame';
        var iframeObj = window.frames[frameId];
        //(��������)this.iterateFlush(window.frames[frameId]);
        if(iframeObj.window.frames.length > 0) {
          for(var i = 0; i < iframeObj.window.frames.length; i++) {
            var childFrame = iframeObj.window.frames[i];
            //��frame�е�����form�ύ
            if(childFrame.document.forms.length > 0) {
              for(var j = 0; j < childFrame.document.forms.length; j++) {
                //form�ύʱ�����쳣,�򽫸�ҳ��ˢ��
                try {
                  childFrame.document.forms[j].submit();
                }
                catch(e) {
                  childFrame.location.reload();
                }
              }
            } else { //û��form,ֱ��ˢ��
              childFrame.location.reload();
            }
          }
        } else {
          if(iframeObj.document.forms.length > 0) {
            for(var j = 0; j < iframeObj.document.forms.length; j++) {
              //form�ύʱ�����쳣,�򽫸�ҳ��ˢ��
              try {
                iframeObj.document.forms[j].submit();
              }
              catch(e) {
                iframeObj.location.reload();
              }
            }
          } else { //û��form,ֱ��ˢ��
            iframeObj.location.reload();
          }
        }
      }
    }
  },
  /**
   * �ݹ�ˢ��(��������)
   * @param {object} iframeObj
   */
  iterateFlush : function(iframeObj) {
    /**����ʹ��frames���ܵõ���Ӧ����*/
    //�����ǰframe���ж��frame,���ٴεݹ�ˢ��
    if(iframeObj.window.frames.length > 0) {
      for(var i = 0; i < iframeObj.window.frames.length; i++) {
        this.iterateFlush(iframeObj.window.frames[i]);
      }
    } else {
      //��frame�е�����form�ύ
      if(iframeObj.document.forms.length > 0) {
        for(var i = 0; i<iframeObj.document.forms.length; i++) {
          //form�ύʱ�����쳣,�򽫸�ҳ��ˢ��
          try {
            iframeObj.document.forms[i].submit();
          }
          catch(e) {
            iframeObj.location.reload();
          }
        }
      } else { //û��form,ֱ��ˢ��
        iframeObj.location.reload();
      }
    }
  }
}