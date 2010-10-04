﻿namespace BluffinPokerClient
{
    partial class LobbyTrainingForm
    {
        /// <summary>
        /// Variable nécessaire au concepteur.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Nettoyage des ressources utilisées.
        /// </summary>
        /// <param name="disposing">true si les ressources managées doivent être supprimées ; sinon, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Code généré par le Concepteur Windows Form

        /// <summary>
        /// Méthode requise pour la prise en charge du concepteur - ne modifiez pas
        /// le contenu de cette méthode avec l'éditeur de code.
        /// </summary>
        private void InitializeComponent()
        {
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.lblStatus = new System.Windows.Forms.ToolStripStatusLabel();
            this.btnLeaveTable = new System.Windows.Forms.ToolStripButton();
            this.btnJoinTable = new System.Windows.Forms.ToolStripButton();
            this.btnAddTable = new System.Windows.Forms.ToolStripButton();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.btnDisconnect = new System.Windows.Forms.ToolStripButton();
            this.btnRefresh = new System.Windows.Forms.ToolStripButton();
            this.lblTitle = new System.Windows.Forms.Label();
            this.tableList = new BluffinPokerGUI.Lobby.PokerTableList();
            this.statusStrip1.SuspendLayout();
            this.toolStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.lblStatus});
            this.statusStrip1.Location = new System.Drawing.Point(0, 225);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(500, 22);
            this.statusStrip1.TabIndex = 4;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // lblStatus
            // 
            this.lblStatus.Name = "lblStatus";
            this.lblStatus.Size = new System.Drawing.Size(88, 17);
            this.lblStatus.Text = "Not Connected";
            // 
            // btnLeaveTable
            // 
            this.btnLeaveTable.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnLeaveTable.Enabled = false;
            this.btnLeaveTable.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnLeaveTable.Name = "btnLeaveTable";
            this.btnLeaveTable.Size = new System.Drawing.Size(73, 22);
            this.btnLeaveTable.Text = "Leave Table";
            this.btnLeaveTable.Click += new System.EventHandler(this.btnLeaveTable_Click);
            // 
            // btnJoinTable
            // 
            this.btnJoinTable.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnJoinTable.Enabled = false;
            this.btnJoinTable.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnJoinTable.Name = "btnJoinTable";
            this.btnJoinTable.Size = new System.Drawing.Size(64, 22);
            this.btnJoinTable.Text = "Join Table";
            this.btnJoinTable.Click += new System.EventHandler(this.btnJoinTable_Click);
            // 
            // btnAddTable
            // 
            this.btnAddTable.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnAddTable.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnAddTable.Name = "btnAddTable";
            this.btnAddTable.Size = new System.Drawing.Size(65, 22);
            this.btnAddTable.Text = "Add Table";
            this.btnAddTable.Click += new System.EventHandler(this.btnAddTable_Click);
            // 
            // toolStrip1
            // 
            this.toolStrip1.GripStyle = System.Windows.Forms.ToolStripGripStyle.Hidden;
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.btnDisconnect,
            this.btnRefresh,
            this.btnAddTable,
            this.btnJoinTable,
            this.btnLeaveTable});
            this.toolStrip1.Location = new System.Drawing.Point(0, 45);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(500, 25);
            this.toolStrip1.TabIndex = 6;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // btnDisconnect
            // 
            this.btnDisconnect.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnDisconnect.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnDisconnect.Name = "btnDisconnect";
            this.btnDisconnect.Size = new System.Drawing.Size(70, 22);
            this.btnDisconnect.Text = "Disconnect";
            this.btnDisconnect.Click += new System.EventHandler(this.btnDisconnect_Click);
            // 
            // btnRefresh
            // 
            this.btnRefresh.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnRefresh.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.Size = new System.Drawing.Size(50, 22);
            this.btnRefresh.Text = "Refresh";
            this.btnRefresh.Click += new System.EventHandler(this.btnRefresh_Click);
            // 
            // lblTitle
            // 
            this.lblTitle.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitle.Location = new System.Drawing.Point(0, 0);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(500, 45);
            this.lblTitle.TabIndex = 5;
            this.lblTitle.Text = "Bluffin Muffin Poker Training Client";
            this.lblTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tableList
            // 
            this.tableList.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableList.Location = new System.Drawing.Point(0, 70);
            this.tableList.Name = "tableList";
            this.tableList.Size = new System.Drawing.Size(500, 155);
            this.tableList.TabIndex = 7;
            this.tableList.OnSelectionChanged += new System.EventHandler(this.tableList_OnSelectionChanged);
            this.tableList.OnChoiceMade += new System.EventHandler(this.tableList_OnChoiceMade);
            // 
            // LobbyTrainingForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(500, 247);
            this.Controls.Add(this.tableList);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.toolStrip1);
            this.Controls.Add(this.lblTitle);
            this.Name = "LobbyTrainingForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Bluffin Muffin Poker Training Client";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.LobbyForm_FormClosed);
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel lblStatus;
        private System.Windows.Forms.ToolStripButton btnLeaveTable;
        private System.Windows.Forms.ToolStripButton btnJoinTable;
        private System.Windows.Forms.ToolStripButton btnAddTable;
        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripButton btnDisconnect;
        private System.Windows.Forms.ToolStripButton btnRefresh;
        private System.Windows.Forms.Label lblTitle;
        private BluffinPokerGUI.Lobby.PokerTableList tableList;

    }
}
